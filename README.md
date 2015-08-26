## atd-java ##
Java client for <a href="http://www.AfterTheDeadline.com/">After The Deadline (AtD)</a> spell, grammar, style and stats checker.

## Usage ##

### Maven coordinates ###
Use the following coordinates in your Maven build:
```xml
<dependency>
  <groupId>ws.bors</groupId>
  <artifactId>atd</artifactId>
  <version>1.1.0</version>
</dependency>
```

### Using the SpellChecker ###
```java
// Use the default English AtD hosted server
SpellChecker spellChecker = new SpellChecker();

// Use a custom hosted AtD server
SpellChecker spellChecker = new SpellChecker(
    "http://YourAtD.Server.url/checkDocument",
    "YourAPI-key"
);

// Change the behavior of the spellChecker
spellChecker.setOptions(new ISpellCheckerOptions() {
    // Preprocess text/html prior to POSTing it to the AtD server
    @Override String processChars(String data) { ... }
    // Decide if this error is to be ignored from the AtD server results
    @Override boolean ignoreWord(String misspelledWord) { ... }
});

// Get all Error(s) grammar | spell | stats | style
Results results = spellChecker.queryServer(data);

// Get a list of misspelled words
List<String> errors = spellChecker.spellErrors("hello world");
```

### Multilanguage ###

After the Deadline server provides 5 languages:

* English (en, default)
* French (fr)
* German (de)
* Spanish (es)
* Portuguese (pt)

If no language is set English is choosen by default. To set another language simply set it:
```java
// Use the default English AtD hosted server
SpellChecker spellChecker = new SpellChecker();
// Change the language to Spanish
spellChecker.setLanguage(Language.SPANISH);
```

### Other AtD client libraries ###

For other AtD client libraries see <a href="http://www.AfterTheDeadline.com/development.slp">AtD's Developer's</a> site.

## Contributing ##

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

#### Releasing Project ####
1. Update POM Version(s) via <br/>
``` mvn release:update-versions -DautoVersionSubmodules=true ```
2. Run Maven build via (order matters as java docs and sources must first be generated then signed) <br/>
``` mvn clean license:format install javadoc:aggregate javadoc:javadoc javadoc:jar source:jar gpg:sign repository:bundle-create ```
3. Check everything in and make a tag
4. Upload bundle via https://oss.sonatype.org
5. Update POM Versions to new SNAPSHOT version via <br/>
``` mvn release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=X.Y.Z-SNAPSHOT ```
6. Check everything in
