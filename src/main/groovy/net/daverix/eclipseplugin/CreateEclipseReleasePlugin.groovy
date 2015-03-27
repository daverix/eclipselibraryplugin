package net.daverix.eclipseplugin

import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar

public class CreateEclipseReleasePlugin implements Plugin<Project> {
    def File outputDir

    @Override
    public void apply(Project project) {
        if (!isAndroidLibraryProject(project)) return

        project.task copyEclipseResources(type: Copy) {
            from project.android.sourceSets.main.resources.srcDirs
            into getResourceDirectory(project)
        }
        project.task copyAndroidManifest(type: Copy) {
            from project.android.sourceSets.main.manifest.srcFile
            into getOutputDirectory(project)
        }
        project.task generateProjectPropertiesFile(type: GenerateEclipseProjectPropertiesFileTask) {
            dependencies getProjectReferences(project)
            targetSdkVersion android.defaultConfig.targetSdkVersion
            destinationDir getOutputDirectory(project)
        }
        project.task generateProjectFile(type: GenerateEclipseProjectFileTask) {
            projectName project.getName()
            destinationDir getOutputDirectory(project)
        }
        project.task generateClassPathFile(type: GenerateEclipseClasspathFileTask) {
            destinationDir getOutputDirectory(project)
        }
        project.task copyDependencyJars(type: Copy) {
            from configurations.compile
            into getLibsDirectory(project)
        }
        project.task generateProjectJar(type: Jar) {
            from getClasspaths(project)
            into getLibsDirectory(project)
        }
        project.task createEclipseRelease {
            dependsOn 'copyEclipseResources',
                'copyAndroidManifest',
                'generateProjectPropertiesFile',
                'generateProjectFile',
                'generateClassPathFile',
                'copyDependencyJars',
                'generateProjectJar'

            mustRunAfter 'assemble'
        }
    }

    static Collection<String> getProjectReferences(Project project) {
        def configuration = project.getConfigurations().getByName("compile")
        def projectConfigurations = configuration.getDependencies().findAll { x ->
            x instanceof ProjectDependency
        }
        return projectConfigurations.collect {
            def projectDependency = it as ProjectDependency
            def projectDep = projectDependency.getDependencyProject()
            return projectDep.relativePath(project.getPath())
        }
    }

    def getOutputDirectory(Project project) {
        if(outputDir == null)
            outputDir = new File("build/output/eclipse/$project.name")

        outputDir.mkdirs()
        return outputDir
    }

    def getLibsDirectory(Project project) {
        def dir = new File(getOutputDirectory(project), 'libs')
        dir.mkdirs()
        return dir
    }

    def getResourceDirectory(Project project) {
        def dir = new File(getOutputDirectory(project), 'res')
        dir.mkdirs()
        return dir
    }

    static def getClasspaths(Project project) {
        return project.android.libraryVariants.collect {
            it.javaCompile.classpath
        }
    }

    static boolean isAndroidLibraryProject(Project project) {
        return project.plugins.find { p -> p instanceof LibraryPlugin }
    }
}
