# Madklub Android App

The native Android app for <a href="https://github.com/petergarnaes/Madklub-website" target="_blank">Madlub website</a>. Utilizes the GraphQL API that runs in that project

## Setup

## Architecture

## TODO

 - Implement restoreable user
 - Implement restoreable user in secure preferences
 - Implement account through Android accounts
 - Implement login screen that uses the API
 - Make main activity the launcher activity, and thoroughly test the "go to login screen" logic
 - Implement UserNetworkModule that provides the appropriate client with the correct session cookie set for injection in user activities. Use either qualifiers or remove NetworkModule from AppComponent and let LoginActivity deal with this alone.
 - 