Eclipse Library plugin
======================

This plugin lets you create an Eclipse ADT Library from any Android Library Module in gradle.

Installation
------------

To install the plugin into your local maven run...

    ./gradlew build install

...or run the following to create a repo inside "pkg"

    ./gradlew build uploadArchives

*Please note that the name of the git root folder is the name of the artifact*

Usage
-----

In you project you want to use this plugin, create a build.gradle that looks something like this.
Replace mavenLocal if you have put it in a repository somewhere else.

*build.gradle*

    buildscript {
        repositories {
            mavenCentral()
            mavenLocal()
        }

        dependencies {
            classpath 'com.android.tools.build:gradle:1.1.0'
            classpath 'net.daverix.eclipselibraryplugin:eclipselibraryplugin:0.1'
        }
    }

    apply plugin: 'com.android.library'
    apply plugin: 'net.daverix.eclipselibrary'

    eclipseLibrary {
        // Optional. Puts the eclipse library into a folder in your project called "pkg"
        outputDir file('pkg')
    }

    android {
        // normal android stuff
        ...
    }

To create an Eclipse Library, execute the following command...

    ./gradlew build createEclipseLibrary

... and it will create an eclipse library for each of your modules where the plugin is applied!


License
-------

    Copyright 2015 David Laurell

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

