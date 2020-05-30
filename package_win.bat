@echo off
jpackage --input dist/ ^
  --name "ASCII Studio" ^
  --main-jar AsciiStudio.jar ^
  --main-class asciistudio.AsciiStudio ^
  --type exe ^
  --java-options "--enable-preview"
@pause