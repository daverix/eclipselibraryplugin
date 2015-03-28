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

public class GenerateEclipseClasspathFileTask extends DefaultTask {
    File destinationDir

    @TaskAction
    def generateFile() {
        def file = project.file("$destinationDir/.classpath")
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
