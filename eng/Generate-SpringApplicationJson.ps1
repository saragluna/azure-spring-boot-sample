#!/usr/bin/env pwsh

#Requires -Version 6.0
#Requires -PSEdition Core

# By default stop for any error.
if (!$PSBoundParameters.ContainsKey('ErrorAction')) {
    $ErrorActionPreference = 'Stop'
}

# Support actions to invoke on exit.
$exitActions = @({
    if ($exitActions.Count -gt 1) {
        Write-Verbose 'Running registered exit actions'
    }
})

# try..finally will also trap Ctrl+C.
try {

    # Enumerate test resources to deploy. Fail if none found.
    $repositoryRoot = "$PSScriptRoot/.." | Resolve-Path
    $root = [System.IO.Path]::Combine($repositoryRoot, "arm-resources") | Resolve-Path
    $envFiles = @()

    'test-resources.json.env' | ForEach-Object {
        Write-Verbose "Checking for '$_' files under '$root'"
        Get-ChildItem -Path $root -Filter "$_" -Recurse | ForEach-Object {
            Write-Verbose "Found env '$($_.FullName)'"
            $envFiles += $_.FullName
        }
    }

    if (!$envFiles) {
        Write-Warning -Message "No env files found under '$root'"
        exit
    }

    $springApplicationJson = @{}
    # Combine the templates
    foreach ($envFile in $envFiles) {
        # Deployment fails if we pass in more parameters than are defined.
        Write-Verbose "Removing unnecessary parameters from template '$($templateFile.jsonFilePath)'"
        $envJson = Get-Content -LiteralPath $envFile | ConvertFrom-Json -AsHashtable
        ForEach ($Key in $envJson.Keys) {
            if (!$springApplicationJson.Contains($Key)) {
                $springApplicationJson.Add($Key, $envJson.$Key)
            }
        }
    }

    $outputFile = [System.IO.Path]::Combine($repositoryRoot, "spring.application.json")

    $environmentText = $springApplicationJson | ConvertTo-Json -Compress

    Set-Content $outputFile -Value $environmentText -Force

} finally {
    $exitActions.Invoke()
}
