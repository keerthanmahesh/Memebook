# Memebook
Memebook is an Android application capable of loading some of the best memes of the early 2010s, allowing the user to add a _CUSTOM TEXT_, and sharing it on their social media of choice.


## Brief
A Meme is an idea, behavior, or style that spreads by means of imitation from person to person within a culture and often carries symbolic meaning representing a particular
phenomenon or theme.
Some of memes from the past decade composed of images with IMPACT text overlays on top of it.

## Implementation
 - The freely available [Meme Maker API](https://mememaker.github.io/API/) is used to display the _memes_ in a clickable `Recycler View`.
 - On scrolling to the bottom, the controller loads more memes, that are auto appended to the `Recycler View`, the API provides basic pagination routes to help with this.
 - On selecting a meme, the app navigates to a detail page, with two `TextView` fields, top and bottom texts.
 - The user can edit both the fields to put _WHATEVER TEXT_ they desire.
 - On clicking the Render `Button`, the top and bottom texts are displayed on the image to present a preview of the edited _meme_.
 - On clicking the Save `Button`, the text be burned-in on the image and the edited image is saved _locally_.
 - On clicking the Share `Button` the app opens a share modal to open the image in supported apps.
 - The app is able to open the saved list of memes created using this app, edit its text, re-save and re-share it. 
