/*
 * Copyright 2015 David Laurell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.daverix.eclipselibraryplugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar

public class EclipseLibraryPlugin implements Plugin<Project> {
    File outputDir

    @Override
    public void apply(Project project) {
        if (!isAndroidLibraryProject(project)) return

        project.extensions.create("eclipseLibrary", EclipseLibraryExtension)
        project.afterEvaluate {
            project.task('copyEclipseResources', type: Copy) {
                from getResourceDirs(project)
                into getResourceOutputDirectory(project)
            }
            project.task('copyAndroidManifest', type: Copy) {
                from project.android.sourceSets.main.manifest.srcFile
                into getOutputDirectory(project)
            }
            project.task('generateProjectPropertiesFile', type: GenerateEclipseProjectPropertiesFileTask) {
                references getProjectReferences(project)
                targetSdkVersion getTargetSdkVersion(project)
                destinationDir getOutputDirectory(project)
            }
            project.task('generateProjectFile', type: GenerateEclipseProjectFileTask) {
                projectName project.getName()
                destinationDir getOutputDirectory(project)
            }
            project.task('generateClassPathFile', type: GenerateEclipseClasspathFileTask) {
                destinationDir getOutputDirectory(project)
            }
            project.task('copyDependencyJars', type: Copy) {
                from project.configurations.compile //TODO: need to sort out which jars to pick
                into getLibsOutputDirectory(project)
            }
            project.task('generateProjectJar', type: Jar) {
                from getClasspaths(project) //TODO: no jar created?
                into getLibsOutputDirectory(project)
            }
            project.task('createEclipseLibrary',
                    dependsOn: [
                            project.tasks.copyEclipseResources,
                            project.tasks.copyAndroidManifest,
                            project.tasks.generateProjectPropertiesFile,
                            project.tasks.generateProjectFile,
                            project.tasks.generateClassPathFile,
                            project.tasks.copyDependencyJars,
                            project.tasks.generateProjectJar
                    ])
        }
    }

    static int getTargetSdkVersion(Project project) {
        def android = project.android as LibraryExtension
        if(android.defaultConfig == null)
            throw new IllegalStateException("android.defaultConfig is null in $project")

        if(android.defaultConfig.targetSdkVersion == null)
            throw new IllegalStateException("android.defaultConfig.targetSdkVersion is null in $project")

        return android.defaultConfig.targetSdkVersion.apiLevel;
    }

    static Collection<String> getProjectReferences(Project project) {
        def configuration = project.getConfigurations().getByName("compile")
        def projectConfigurations = configuration.getDependencies().findAll { x ->
            x instanceof ProjectDependency
        }
        return projectConfigurations.collect {
            def projectDependency = it as ProjectDependency
            def projectDep = projectDependency.getDependencyProject()
            def strBuilder = new StringBuilder();
            for(int i=0;i<projectDep.depth;i++) {
                strBuilder.append("../")
            }
            strBuilder.append(projectDep.name)
            return strBuilder.toString()
        }
    }

    static def getResourceDirs(Project project) {
        def android = project.android as LibraryExtension
        return android.sourceSets.main.res.srcDirs
    }

    static def getClasspaths(Project project) {
        return project.android.libraryVariants.collect {
            it.javaCompile.classpath
        }
    }

    static boolean isAndroidLibraryProject(Project project) {
        return project.plugins.find { p -> p instanceof LibraryPlugin }
    }

    def getOutputDirectory(Project project) {
        if(outputDir == null) {
            outputDir = project.file("build/outputs/eclipse")

            if(project.hasProperty("eclipseLibrary")) {
                def eclipseLibrary = project.eclipseLibrary as EclipseLibraryExtension
                if(eclipseLibrary.outputDir != null) {
                    outputDir = eclipseLibrary.outputDir
                }
            }
        }

        outputDir.mkdirs()
        return outputDir
    }

    def getLibsOutputDirectory(Project project) {
        def outputDir = getOutputDirectory(project)
        def dir = project.file("$outputDir/libs")
        dir.mkdirs()
        return dir
    }

    def getResourceOutputDirectory(Project project) {
        def outputDir = getOutputDirectory(project)
        def dir = project.file("$outputDir/res")
        dir.mkdirs()
        return dir
    }
}
