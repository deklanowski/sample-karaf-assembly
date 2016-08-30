# sample-karaf-assembly
This illustrates how to build a Karaf 4.x custom assembly by just taking a minimal distribution and 
adding a project feature set on top. 

# Assembly
The assembly module contains the karaf-maven-plugin configuration which directs how the assembly is built. 

# Sample Project
The sample project consists of two modules comprising an api and an implementation bundle and
a karaf feature descriptor file to be included in the assembly. 

# Issues
In karaf 4.0.5 and 4.0.6 there is an issue with nested features which explicitly declare a version
when that version is a development snapshot. 

For example, take our features descriptor for the sample project:
```
    <feature name="api" version="${project.version}">
        <bundle>mvn:org.deklanowski.karaf/api/${project.version}</bundle>
    </feature>

    <feature name="impl" version="${project.version}">
        <feature version="${project.version}">api</feature> 
        <!--feature>api</feature-->
        <bundle>mvn:org.deklanowski.karaf/impl/${project.version}</bundle>
    </feature>
```
If we explicitly include the version of the nested api feature inside the impl feature then 
you will get the following output when building the assembly:

```
[INFO] Reactor Summary:
[INFO] 
[INFO] sample feature ..................................... SUCCESS [  0.116 s]
[INFO] sample karaf feature repository .................... SUCCESS [  0.763 s]
[INFO] sample karaf assembly configuration ................ FAILURE [  2.727 s]
[INFO] sample feature api ................................. SKIPPED
[INFO] sample feature impl ................................ SKIPPED
[INFO] sample assembly reactor ............................ SKIPPED
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5.178 s
[INFO] Finished at: 2016-08-30T10:52:02+01:00
[INFO] Final Memory: 23M/399M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.karaf.tooling:karaf-maven-plugin:4.0.6:assembly (default-assembly) on project assembly: Unable to build assembly: Could not find matching feature for api/1.0-SNAPSHOT -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
[ERROR] 
[ERROR] After correcting the problems, you can resume the build with the command
[ERROR]   mvn <goals> -rf :assembly
```

Excluding the version number solves the issue. With a little debugging the issue lurks in


