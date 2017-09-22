# Madklub Android App

The native Android app for <a href="https://github.com/petergarnaes/Madklub-website" target="_blank">Madlub website</a>. Utilizes the GraphQL API that runs in that project

## Setup

## Architecture

## TODO

 - <s>Implement account through Android accounts</s>
    * Add proper account selection
    * <s>Handle no accounts in `accountManager.getAccountsByType` by sending them to AuthenticatorActivity<s>
    * <s>Make network interface which adds operators to the network observable that on a 401 status invalidates token (through `mAccountManager.invalidateAuthToken`) and repeats once. The account manager should take over from there and call our authenticator that knows how to log in again to refresh<s>
 - <s>Implement login screen that uses the API</s>
 - <s>Make main activity the launcher activity, and thoroughly test the "go to login screen" logic</s>
 - <s>Implement authenticated network access.<s>
 - <s>Add logic that makes sure data is fetched/re-fetched in case user was thrown into authentication activity</s>
 - Implement <a href="https://github.com/team-supercharge/ShimmerLayout" target="_blank">ShimmerLayout</a> and/or <a href="https://github.com/sharish/ShimmerRecyclerView" target="_blank">ShimmerRecyclerView</a> for loading dinners
 - <s>Implement View type for dinners where user cooks</s>
 - Headers showing month we are in.
    * Make headers sticky
    * Make action bar disappear on scroll, so there is more room for list and sticky header
 - Add floating action button to create new dinners
    * Implement fancy calendar selection, maybe with Squares claendar dialog view?
 - Dinnerclub detail activities
    * One for participant view
    * One for cook view
 - Fancy drag-and-drop for your dinners where user cook?
    * When drag, add blank elements to signify free dates
 - Create notifications that tell the user there is a dinnerclub the same day (if there is of course)
 - Settings activity
    * Enable/disable reminder notification
    * Set notification reminder time
    * Log out