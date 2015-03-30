package net.daverix.eclipselibraryplugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

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
}
