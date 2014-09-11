ICNDB_AS
========

Rewritten ICNDB app using Android Studio with gradle builds.

ICNDB == Internet Chuck Norris Database

The application uses the `RestTemplate` from the Spring Android project to access
the restful service at http://icndb.com. It then uses Google's Gson library to
convert the response into classes, and updates the display with a new joke.

The gradle build includes the needed dependencies as well as an androidTestCompile
dependency for the Robotium project. A test is provided to check that the app is
working properly, which can be executed with the `gradlew connectedCheck` task. Be
sure to have at least one emulator and/or connected device available when you run
the tests.

The app has two buildTypes, debug and release. The name of the "hero" is different
in each type. Java's keytool was used to generate a certificate that was used to
sign the jar file for the release version.

Enjoy,

Ken Kousen
ken(dot)kousen(at)kousenit.com
