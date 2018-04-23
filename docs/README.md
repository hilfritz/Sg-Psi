Android-HBMvp

Disc



Timber
https://caster.io/episodes/episode-14-logging-with-timber/


RELEASE

  FOR PUBLISHING TO JCENTER see: https://medium.com/@daniellevass/how-to-publish-your-android-studio-library-to-jcenter-5384172c4739
  Notes:
  1. Ended up renaming the tasks inside android-release-aar.gradle, added suffix 'A' to most of them
  - according to the article below, you can not create another existing task
  - http://stackoverflow.com/a/11159071/2412615
  2. Ran the following code inside the library project:
      ./gradlew clean build generateRelease
      * - if you get permission denied, try to run command "chmod +x gradlew"
