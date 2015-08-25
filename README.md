## atd-java ##
Java client for <a href="http://www.AfterTheDeadline.com/">After The Deadline (AtD)</a> spell and grammar checker.

## Usage ##

### Maven coordinates ###
Use the following dependencies in your Maven build:
```xml
<dependency>
  <groupId>ws.bors</groupId>
  <artifactId>atd</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Defining a SpellChecker ###
```java
// Use the default English AtD hosted server
SpellChecker spellChecker = new SpellChecker();

// Use a custom hosted AtD server
SpellChecker spellChecker = new SpellChecker("http://YourAtD.Server.url/checkDocument", "YourAPI-key");

// Change the behavior of the spellChecker
spellChecker.setOptions(new ISpellCheckerOptions() {
    // Your custom impl
});

// Get all Error(s)
Results results = spellChecker.queryServer(text);

// Get a list of misspelled words
List<String> errors = spellChecker.spellErrors("hello world");
```

### Other AtD client libraries ###

For other AtD client libraries see <a href="http://www.AfterTheDeadline.com/development.slp">AtD's Developer's</a> site.

## Releasing Project ##
1. Update POM Version(s) via <br/>
``` mvn release:update-versions -DautoVersionSubmodules=true```
2. Run Maven build via (order matters as java docs and sources must first be generated than signed) <br/>
``` mvn clean license:format install javadoc:aggregate javadoc:javadoc javadoc:jar source:jar gpg:sign repository:bundle-create ```
3. Check everything in and make a tag
4. Upload bundle via https://oss.sonatype.org.
   If you upload file by file then for the pom.asc, enter "pom.asc" in the "extension" field
5. Update POM Versions to new SNAPSHOT version via <br/>
``` mvn release:update-versions -DautoVersionSubmodules=true```
6. Check everything in
