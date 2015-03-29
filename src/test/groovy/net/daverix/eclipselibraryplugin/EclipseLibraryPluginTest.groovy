package net.daverix.eclipselibraryplugin

import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.ide.visualstudio.tasks.GenerateProjectFileTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertThat

public class EclipseLibraryPluginTest {
    Project project

    @Before
    public void setUp() {
        project = ProjectBuilder.builder().build()
        project.plugins.apply LibraryPlugin
        project.plugins.apply EclipseLibraryPlugin
    }

    @Test
    public void projectShouldHaveEclipseLibraryExtension() {
        assertThat(project.extensions.eclipseLibrary, instanceOf(EclipseLibraryExtension.class))
    }

    @Test
    public void copyEclipseResourcesShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.copyEclipseResources, instanceOf(Copy.class))
        }
    }

    @Test
    public void copyAndroidManifestShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.copyAndroidManifest, instanceOf(Copy.class))
        }
    }

    @Test
    public void generateProjectPropertiesFileShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.generateProjectPropertiesFile, instanceOf(GenerateEclipseProjectPropertiesFileTask.class))
        }
    }

    @Test
    public void generateProjectFileShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.generateProjectFile, instanceOf(GenerateProjectFileTask.class))
        }
    }

    @Test
    public void generateClasspathFileShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.generateClassPathFile, instanceOf(GenerateEclipseClasspathFileTask.class))
        }
    }

    @Test
    public void copyDependencyJarsShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.copyDependencyJars, instanceOf(Copy.class))
        }
    }

    @Test
    public void generateProjectJarShouldBeAddedToTheProject() {
        project.afterEvaluate {
            assertThat(project.tasks.generateProjectJar, instanceOf(Jar.class))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnCopyEclipseResources() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.copyEclipseResources))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnCopyAndroidManifest() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.copyAndroidManifest))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnGenerateProjectPropertiesFile() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.generateProjectPropertiesFile))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnGenerateProjectFile() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.generateProjectFile))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnGenerateClassPathFile() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.generateClassPathFile))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnCopyDependencyJars() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.copyDependencyJars))
        }
    }

    @Test
    public void createEclipseLibraryShouldDependOnGenerateProjectJar() {
        project.afterEvaluate {
            assertThat(project.tasks.createEclipseLibrary.dependsOn, hasItem(project.tasks.generateProjectJar))
        }
    }
}
