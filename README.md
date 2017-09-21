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
 - Implement <a href="https://github.com/team-supercharge/ShimmerLayout" target="_blank">ShimmerLayout</a> and/or <a href="https://github.com/sharish/ShimmerRecyclerView" target="_blank">ShimmerRecyclerView</a> for loading dinners
 - Implement View type for dinners where user cooks
 - Headers showing month we are in.
    * Make headers sticky
    * Make action bar disappear on scroll, so there is more room for list and sticky header
 - Add floating action button to create new dinners
    * Implement fancy calendar selection, maybe with Squares claendar dialog view?
 - Fancy drag-and-drop for your dinners where user cook?
    * When drag, add blank elements to signify free dates
 - Settings activity
    * Enable/disable reminder notification
    * Set notification reminder time
    * Log out