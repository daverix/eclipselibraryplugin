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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;

public class GenerateEclipseProjectFileTask extends DefaultTask {
    File destinationDir
    String projectName

    @TaskAction
    def generateFile() {
        def file = project.file("$destinationDir/.project")
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
