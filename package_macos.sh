jpackage --input 'dist/' \
  --name 'ASCII Studio' \
  --main-jar 'AsciiStudio.jar' \
  --main-class 'asciistudio.AsciiStudio' \
  --icon 'icon/icon.icns' \
  --type 'dmg' \
  --java-options '--enable-preview' \
  --app-version '4.0' \
  --copyright 'Copyright © 2016-2020 Ian Martinez' \
  --dest 'release/'