# PowerShell script to rename packages from com.zeonvillan.test to com.zeonvillan.couponsystem

Write-Host "Starting package rename from com.zeonvillan.test to com.zeonvillan.couponsystem..."

# Create new directory structure
Write-Host "Creating new directory structure..."
$newDirs = @(
    "src\main\java\com\zeonvillan\couponsystem\entity",
    "src\main\java\com\zeonvillan\couponsystem\repository", 
    "src\main\java\com\zeonvillan\couponsystem\service",
    "src\main\java\com\zeonvillan\couponsystem\controller",
    "src\main\java\com\zeonvillan\couponsystem\dto",
    "src\main\java\com\zeonvillan\couponsystem\model",
    "src\main\java\com\zeonvillan\couponsystem\exception",
    "src\main\java\com\zeonvillan\couponsystem\config",
    "src\test\java\com\zeonvillan\couponsystem\service",
    "src\test\java\com\zeonvillan\couponsystem\controller"
)

foreach ($dir in $newDirs) {
    if (!(Test-Path $dir)) {
        New-Item -ItemType Directory -Path $dir -Force
        Write-Host "Created directory: $dir"
    }
}

# Function to update package name in a file
function Update-PackageName {
    param($filePath)
    
    if (Test-Path $filePath) {
        $content = Get-Content $filePath -Raw
        $updatedContent = $content -replace "package com\.zeonvillan\.test", "package com.zeonvillan.couponsystem"
        $updatedContent = $updatedContent -replace "import com\.zeonvillan\.test", "import com.zeonvillan.couponsystem"
        Set-Content $filePath $updatedContent
        Write-Host "Updated package names in: $filePath"
    }
}

# Copy and update files
Write-Host "Copying and updating files..."

# Get all Java files from old structure
$oldFiles = Get-ChildItem -Path "src\main\java\com\zeonvillan\test" -Recurse -Filter "*.java"
$oldTestFiles = Get-ChildItem -Path "src\test\java\com\zeonvillan\test" -Recurse -Filter "*.java"

# Copy main source files
foreach ($file in $oldFiles) {
    $relativePath = $file.FullName.Replace((Get-Location).Path + "\src\main\java\com\zeonvillan\test\", "")
    $newPath = "src\main\java\com\zeonvillan\couponsystem\$relativePath"
    
    # Create directory if it doesn't exist
    $newDir = Split-Path $newPath -Parent
    if (!(Test-Path $newDir)) {
        New-Item -ItemType Directory -Path $newDir -Force
    }
    
    Copy-Item $file.FullName $newPath
    Update-PackageName $newPath
    Write-Host "Copied and updated: $($file.Name)"
}

# Copy test files
foreach ($file in $oldTestFiles) {
    $relativePath = $file.FullName.Replace((Get-Location).Path + "\src\test\java\com\zeonvillan\test\", "")
    $newPath = "src\test\java\com\zeonvillan\couponsystem\$relativePath"
    
    # Create directory if it doesn't exist
    $newDir = Split-Path $newPath -Parent
    if (!(Test-Path $newDir)) {
        New-Item -ItemType Directory -Path $newDir -Force
    }
    
    Copy-Item $file.FullName $newPath
    Update-PackageName $newPath
    Write-Host "Copied and updated test: $($file.Name)"
}

Write-Host "Package rename completed!"
Write-Host "You can now delete the old directory: src\main\java\com\zeonvillan\test"
Write-Host "You can now delete the old test directory: src\test\java\com\zeonvillan\test"
Write-Host ""
Write-Host "Next steps:"
Write-Host "1. Run: .\mvnw clean compile"
Write-Host "2. Run: .\mvnw test"
Write-Host "3. Run: .\mvnw spring-boot:run"
