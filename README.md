# Madklub Android App

The native Android app for <a href="https://github.com/petergarnaes/Madklub-website" target="_blank">Madlub website</a>. Utilizes the GraphQL API that runs in that project

## Setup

## Architecture

## TODO

 - Enable Apollo caching
 - <s>Implement account through Android accounts</s>
    * Add proper account selection
    * <s>Handle no accounts in `accountManager.getAccountsByType` by sending them to AuthenticatorActivity</s>
    * <s>Make network interface which adds operators to the network observable that on a 401 status invalidates token (through `mAccountManager.invalidateAuthToken`) and repeats once. The account manager should take over from there and call our authenticator that knows how to log in again to refresh</s>
 - <s>Implement login screen that uses the API</s>
 - <s>Make main activity the launcher activity, and thoroughly test the "go to login screen" logic</s>
 - <s>Implement authenticated network access.</s>
 - <s>Add logic that makes sure data is fetched/re-fetched in case user was thrown into authentication activity</s>
 - <s>Implement configuration change persistence</s>
 - Implement <a href="https://github.com/team-supercharge/ShimmerLayout" target="_blank">ShimmerLayout</a> and/or <a href="https://github.com/sharish/ShimmerRecyclerView" target="_blank">ShimmerRecyclerView</a> for loading dinners
 - <s>Implement View type for dinners where user cooks</s>
    * <s>Participation count</s>
    * <s>Implement participation shows correctly</s>
    * Shopping icon should perform mutation
    * Cancelling participation icon should perform mutation
 - Dinnerclub infinite scroll
    * <s>Implement scroll listener</s>
    * <s>If nothing loaded, tell user end is reached</s>
    * load more when scrolled to bottom
    * load previous when scrolled to top, remember to reset scroll
    * <s>If list is to short, replace bottom loader</s>
        * Decide what to do when list is short. Do we do "Load more" button or make it try to load next segment?
 - Dinnerclub list refresh
    * On refresh reset date range we look at
 - Headers showing month we are in.
    * Make headers sticky
    * Make action bar disappear on scroll, so there is more room for list and sticky header
 - Add floating action button to create new dinners
    * Implement fancy calendar selection, maybe with Squares claendar dialog view?
 - Dinnerclub detail activities
    * One for participant view
        * Fix transition when selecting element partially underneath statusbar. <a href="https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition#5-customizing-shared-elements-transition">Link</a>
        * <s>Make shared transition of appropriate icon depending on if participant or cook </s>
        * <s>Make mail icon to edit icon</s>, and make it show a pop-up dialog for typing a new meal, and perform mutation
        * <s>Diplay data from server</s>, mix properly with local data
        * implement mutations for cancelled, shopped, participating, price etc.
    * One for cook view
 - Fancy drag-and-drop for your dinners where user cook?
    * When drag, add blank elements to signify free dates
 - Create notifications that tell the user there is a dinnerclub the same day (if there is of course)
 - Settings activity
    * Enable/disable reminder notification
    * Set notification reminder time
    * Log out