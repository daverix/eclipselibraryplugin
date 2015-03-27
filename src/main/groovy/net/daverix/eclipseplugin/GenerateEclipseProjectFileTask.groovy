package net.daverix.eclipseplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;

public class GenerateEclipseProjectFileTask extends DefaultTask {
    File destinationDir
    String projectName

    @TaskAction
    def generateFile() {
        def file = new File(destinationDir, ".project")
        file.text = "<projectDescription>\n" +
                "   <name>$projectName</name>\n" +
                "   <comment/>\n" +
                "   <projects></projects>\n" +
                "   <buildSpec>\n" +
                "       <buildCommand>\n" +
                "           <name>com.android.ide.eclipse.adt.ResourceManagerBuilder</name>\n" +
                "           <arguments></arguments>\n" +
                "       </buildCommand>\n" +
                "       <buildCommand>\n" +
                "           <name>com.android.ide.eclipse.adt.PreCompilerBuilder</name>\n" +
                "           <arguments></arguments>\n" +
                "       </buildCommand>\n" +
                "       <buildCommand>\n" +
                "           <name>org.eclipse.jdt.core.javabuilder</name>\n" +
                "           <arguments></arguments>\n" +
                "       </buildCommand>\n" +
                "       <buildCommand>\n" +
                "           <name>com.android.ide.eclipse.adt.ApkBuilder</name>\n" +
                "           <arguments></arguments>\n" +
                "       </buildCommand>\n" +
                "   </buildSpec>\n" +
                "   <natures>\n" +
                "       <nature>com.android.ide.eclipse.adt.AndroidNature</nature>\n" +
                "       <nature>org.eclipse.jdt.core.javanature</nature>\n" +
                "   </natures>\n" +
                "</projectDescription>"
    }
}
