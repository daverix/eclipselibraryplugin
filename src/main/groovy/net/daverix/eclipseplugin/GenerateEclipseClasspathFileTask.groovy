package net.daverix.eclipseplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;

public class GenerateEclipseClasspathFileTask extends DefaultTask {
    File destinationDir

    @TaskAction
    def generateFile() {
        def file = new File(destinationDir, ".classpath")
        file.text = "<classpath>\n" +
                "   <classpathentry exported=\"true\" kind=\"con\" path=\"com.android.ide.eclipse.adt.LIBRARIES\"/>\n" +
                "   <classpathentry kind=\"con\" path=\"com.android.ide.eclipse.adt.ANDROID_FRAMEWORK\"/>\n" +
                "   <classpathentry exported=\"true\" kind=\"con\" path=\"com.android.ide.eclipse.adt.DEPENDENCIES\"/>\n" +
                "   <classpathentry kind=\"src\" path=\"src\"/>\n" +
                "   <classpathentry kind=\"src\" path=\"gen\"/>\n" +
                "   <classpathentry kind=\"output\" path=\"bin/classes\"/>\n" +
                "</classpath>"
    }
}
