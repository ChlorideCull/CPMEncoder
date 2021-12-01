# CPM GitHub Link Encoder
Encodes a link to a file on GitHub that [CustomPlayerModels](https://github.com/tom5454/CustomPlayerModels) can read, using hidden functionality.

In particular, this generates an "overlay" image to place on your skin, which, when placed, makes it load the model data from a file on GitHub instead of a Gist.

This tool was made to address shortcomings regarding compatibility with other mods like [Ears](https://github.com/unascribed/Ears) which UV maps a lot of the areas otherwise used by CPM, showing ghastly lines of random colors. As part of that this tool makes sure the link is placed inside the area that CPM blacks out, which allows us to set the Alpha of those pixels as low as it goes without going completely invisible - making it invisible to the naked eye but not optimized away by the skin servers.

### Limitations

1. The `user/repo/branch/filename` part of the URL can be at most 32 characters before it overflows into an area that uses the alpha channel, and thus breaks the trick. There are a lot of places to save space here, so it shouldn't be much of an issue - for example you don't need a file extension, and the branch can be a single letter.
2. It's hardcoded to assume you have a "classic" skin, which *shouldn't* matter - it's only used for knowing which channels are available for data encoding, and is the same for both "classic" and "slim" skins in the areas we care about. If something explodes however, this is probably the culprit.
3. The earliest version of CPM that supports GitHub access is 0.0.9a, if you're the kind of person who plays with old mods.

### Usage

Call it with `<URL you get when you open the file 'Raw' on GitHub> <place to save the overlay PNG (NOT YOUR SKIN)>` and then put the overlay PNG on your skin with something like GIMP.

Once detected by CPM, it will unlock the 'Export Update' button in the editor. You should never use the 'Export' or 'Export & Upload' buttons, as it will undo your hard work.

Note that you need to work with the encoded model file locally with Git and push it, as the web editor introduces a line break at the end which the mod balks at.

### Future

Ideally, this shouldn't have to exist - if CPM could fill the first block before going on to the next instead of going line by line, you could use a regular Gist without having to rely on undocumented features.

In addition, I'd love to see the ability to 'link' models from arbitrary servers instead of being stuck with a Gist, or just load binaries from GitHub instead of forcing Base64 encoding.