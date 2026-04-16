# bodymovin
After Effects plugin for exporting animations to svg/canvas/html + js or natively on Android and iOS through [Lottie](https://medium.com/airbnb-engineering/introducing-lottie-4ff4a0afac0e)

Installation instructions:
- Extract content and search for the .zxp file from '/build/extension'
- Use the [ZXP installer](http://aescripts.com/learn/zxp-installer/) from aescripts.com.

## V 5.12.1
- FIX: Transform Effect opacity reset
- FEATURE: esm support
- FIX: Fix transform matrix precalc when adding effects
- IMPROVEMENT: canvas renderer signifcantly improved in performance
- IMPROVEMENT: improved text support for emojis
- IMPROVEMENT: text renderer performance improvement when rendering out of the work area
- FIX: canvas expressions render order

## V 5.12.0
- FEATURE: Transform Effect support
- FIX: play / pause behavior on worker
- IMPROVEMENT: move text rendering to render phase
- FEATURE: expose global object _lottieGlobal for expressions

## V 5.11.0
- FEATURE: Essential Graphics support
- FEATURE: slots support
- FIX: update data when updateDocumentData is called
- FIX: using unregistered renderer or missing path
- FIX: added destroy null check
- FEATURE: Implement setLoop in Web Worker

## V 5.10.2
- FEATURE: add setLoop method
- FEATURE: support for all matte masks in canvas
- FEATURE: support for alpha matte in canvas worker
- FEATURE: support for canvas renderer in Safari
- FEATURE: add DOM loaded event for canvas worker
- FIX: tint effect opacity

## V 5.10.1
- FIX: track matte masks transform fix

## V 5.10.0
- FIX: light versions referencing expressions interfaces
- FEATURE: Add support for new track masks by layer index
- FEATURE: Add support for zigzag (thanks @mbasaglia)
- FEATURE: Add support for offset path (thanks @mbasaglia)
- FEATURE: Customizable canvas renderer width and height
- FIX: Workaround for banner validation

## V 5.9.6
- FIX: Bodymovin export missing comps
- FIX: Disambiguate bevelEmboss properties (thanks @mbasaglia)
- FEATURE: Add support for image export individual settings via annotations

## V 5.9.5
- FIX: Expand the hybrid shape bounding box on stroke
- FEATURE: support filter size on drop shadow property
- FIX: canvas worker - handle destroying animation instance before data is loaded
- FIX: canvas worker = pass own canvas fix
- FIX: bodymovin extension out of storage
- FEATURE: integration with canilottie

## V 5.9.4
- FIX: support for all marker definitions
- FIX: improve composable filters
- FIX: strokes with correct width on fonts

## V 5.9.3
- FEATURE: Add support for isPaused property on lottie worker
- FEATURE: Adds support for changing volume based on the audio layer data
- FEATURE: add support for removeEventListener on worker
- FEATURE: add support for updateDocumentData on worker
- FEATURE: add text as font support on worker
- FIX: avoid crash when script does not have src

## V 5.9.2
- FEATURE: add support for playsegments and blend mode on lottie worker
- FIX: Fix ts typing for loadAnimation parameter
- FEATURE: allows to set width and height of svg from config
- FIX: Fix getMarkerData bug
- FEATURE: Add type for goToAndPlay & goToAndStop
- FIX: add time to list of code for preventing removal
- FIX: glyphs removed from DOM before reloading

## V 5.9.1
- FIX: navigator validation for SSR

## V 5.9.0
- FEATURE: Added support for using shapes as animated characters
- FIX: added methods used by expressions to an array to prevent treeshaking
- DEV: Migrated code to imports
- DEV: Use Rollup for build process

## V 5.8.1
- FEATURE: added more methods available for the lottie worker player

## V 5.8.0
- FEATURE: lottie player as a worker support
- FEATURE: added content-visibility prop support
- FEATURE: added header check for json type (improves initial parsing specially on large files)
- FEATURE: added lottie.useWebWorker method to support loading file on a separate web worker (different than the first feature)
- FEATURE: added frame rate to comp on export (not supported by payers yet)
- FIX: random seed expression fix

# Lottie + Bodymovin
Lottie is the native engine that Airbnb's awesome team built. It uses Bodymovin as the animation exporter and is the ideal complement for getting animations to play natively everywhere.
Follow these links to get each player:
- [Android's player](https://github.com/airbnb/lottie-android)
- [iOS's player](https://github.com/airbnb/lottie-ios)
- [React Native's wrapper](https://github.com/airbnb/lottie-react-native)

## Lottie and AVD
Some animations can be exported for Android using the AVD format.
It can fit for some cases where you'll gain a performance improvement.
But Lottie brings much more features, a level of animation control and dynamic loading that couldn't be achieved with avd.
Here's a [link](http://airbnb.io/lottie/lottie-avd.html) with a full comparison of both technologies.

### After installing
- Go to Edit > Preferences > General > and check on "Allow Scripts to Write Files and Access Network"

# HTML player installation
```bash
# with npm
npm install lottie-web

# with bower
bower install bodymovin
```
Or you can use the script file from here:
https://cdnjs.com/libraries/bodymovin
Or get it directly from the AE plugin clicking on Get Player

# Demo
[See a basic implementation here.](https://codepen.io/airnan/project/editor/ZeNONO/) <br/>

# Examples
[See examples on codepen.](http://codepen.io/collection/nVYWZR/) <br/>

## How it works
[Here's](https://www.youtube.com/watch?v=5XMUJdjI0L8) a video tutorial explaining how to export a basic animation and load it in an html page <br />
### After Effects
- Open your AE project and select the bodymovin extension on Window > Extensions > bodymovin
- A Panel will open with a Compositions tab listing all of your Project Compositions.
- Select the composition you want to export.
- Select a Destination Folder.
- Click Render
- look for the exported json file (if you had images or AI layers on your animation, there will be an images folder with the exported files)

### HTML
- get the lottie.js file from the build/player/ folder for the latest build
- include the .js file on your html (remember to gzip it for production)
```html
<script src="js/lottie.js" type="text/javascript"></script>
```
You can call lottie.loadAnimation() to start an animation.
It takes an object as a unique param with:
- animationData: an Object with the exported animation data.
- path: the relative path to the animation object. (animationData and path are mutually exclusive)
- loop: true / false / number
- autoplay: true / false it will start playing as soon as it is ready
- name: animation name for future reference
- renderer: 'svg' / 'canvas' / 'html' to set the renderer
- container: the dom element on which to render the animation


It returns the animation instance you can control with play, pause, setSpeed, etc.

```js
lottie.loadAnimation({
  container: element, // the dom element that will contain the animation
  renderer: 'svg',
  loop: true,
  autoplay: true,
  path: 'data.json' // the path to the animation json
});
```

#### Composition Settings:
Check this wiki page for an explanation for each setting.
https://github.com/airbnb/lottie-web/wiki/Composition-Settings

## Usage
Animation instances have these main methods: 
### play 

***
### stop

***
### pause

***
### setLocationHref(href)
- `href`: usually pass as `location.href`. Its useful when you experience mask issue in safari where your url does not have `#` symbol.

***
### setSpeed(speed)
- `speed`: 1 is normal speed.

***
### goToAndStop(value, isFrame)
- `value`: numeric value.
- `isFrame`: defines if first argument is a time based value or a frame based (default false).

***
### goToAndPlay(value, isFrame)
- `value`: numeric value.
- `isFrame`: defines if first argument is a time based value or a frame based (default false).

***
### setDirection(direction)
- `direction`: 1 is forward, -1 is reverse.

***
### playSegments(segments, forceFlag)
- `segments`: array. Can contain 2 numeric values that will be used as first and last frame of the animation. Or can contain a sequence of arrays each with 2 numeric values.
- `forceFlag`: boolean. If set to false, it will wait until the current segment is complete. If true, it will update values immediately.
***
### setSubframe(useSubFrames)
- `useSubFrames`:  If false, it will respect the original AE fps. If true, it will update on every requestAnimationFrame with intermediate values. Default is true.
***
### destroy()
***
### getDuration(inFrames)
- `inFrames`:  If true, returns duration in frames, if false, in seconds.
***

### Aditional methods:
- updateTextDocumentData -- updates a text layer's data  
[More Info](https://github.com/airbnb/lottie-web/wiki/TextLayer.updateDocumentData)
***

### Lottie has several global methods that will affect all animations:
**lottie.play()** -- with 1 optional parameter **name** to target a specific animation <br/>
**lottie.stop()** -- with 1 optional parameter **name** to target a specific animation <br/>
**lottie.goToAndStop(value, isFrame, name)** -- Moves an animation with the specified name playback to the defined time. If name is omitted, moves all animation instances.<br />
**lottie.setSpeed()** -- first argument speed (1 is normal speed) -- with 1 optional parameter **name** to target a specific animation <br/>
**lottie.setDirection()** -- first argument direction (1 is normal direction.) -- with 1 optional parameter **name** to target a specific animation <br/>
**lottie.searchAnimations()** -- looks for elements with class "lottie" or "bodymovin" <br/>
**lottie.loadAnimation()** -- Explained above. returns an animation instance to control individually. <br/>
**lottie.destroy(name)** -- Destroys an animation with the specified name. If name is omitted, destroys all animation instances. The DOM element will be emptied.<br />
**lottie.registerAnimation()** -- you can register an element directly with registerAnimation. It must have the "data-animation-path" attribute pointing at the data.json url<br />
**lottie.getRegisteredAnimations()** -- returns all animations instances<br />
**lottie.setQuality()** -- default 'high', set 'high','medium','low', or a number > 1 to improve player performance. In some animations as low as 2 won't show any difference.<br />
**lottie.setLocationHref()** -- Sets the relative location from where svg elements with ids are referenced. It's useful when you experience mask issues in Safari.<br />
**lottie.freeze()** -- Freezes all playing animations or animations that will be loaded<br />
**lottie.unfreeze()** -- Unfreezes all animations<br />
**lottie.inBrowser()** -- true if the library is being run in a browser<br />
**lottie.resize()** -- Resizes all animation instances<br />

## Events
- onComplete
- onLoopComplete
- onEnterFrame
- onSegmentStart

you can also use addEventListener with the following events:
- complete
- loopComplete
- enterFrame
- segmentStart
- config_ready (when initial config is done)
- data_ready (when all parts of the animation have been loaded)
- loaded_images (when all image loads have either succeeded or errored)
- DOMLoaded (when elements have been added to the DOM)
- destroy

#### Other loading options
- if you want to use an existing canvas to draw, you can pass an extra object: 'renderer' with the following configuration:
```js
lottie.loadAnimation({
  container: element, // the dom element
  renderer: 'svg',
  loop: true,
  autoplay: true,
  animationData: animationData, // the animation data
  rendererSettings: {
    context: canvasContext, // the canvas context
    scaleMode: 'noScale',
    clearCanvas: false,
    progressiveLoad: false, // Boolean, only svg renderer, loads dom elements when needed. Might speed up initialization for large number of elements.
    hideOnTransparent: true, //Boolean, only svg renderer, hides elements when opacity reaches 0 (defaults to true)
    className: 'some-css-class-name'
  }
});
```
Doing this you will have to handle the canvas clearing after each frame
<br/>
Another way to load animations is adding specific attributes to a dom element.
You have to include a div and set it's class to lottie.
If you do it before page load, it will automatically search for all tags with the class "lottie".
Or you can call lottie.searchAnimations() after page load and it will search all elements with the class "lottie".
<br/>
- add the data.json to a folder relative to the html
- create a div that will contain the animation.
<br/>
 **Required**
 <br/>
 . a class called "lottie"
 . a "data-animation-path" attribute with relative path to the data.json
 <br/>
**Optional**
<br/>
 . a "data-anim-loop" attribute
 . a "data-name" attribute to specify a name to target play controls specifically
 <br/>
 **Example**
 <br/>
```html
 <div style="width:1067px;height:600px" class="lottie" data-animation-path="animation/" data-anim-loop="true" data-name="ninja"></div>
```
<br/>



## Preview
You can preview or take an svg snapshot of the animation to use as poster. After you render your animation, you can take a snapshot of any frame in the animation and save it to your disk. I recommend to pass the svg through an svg optimizer like https://jakearchibald.github.io/svgomg/ and play around with their settings.<br/>

## Recommendations

### Files
If you have any images or AI layers that you haven't converted to shapes (I recommend that you convert them, so they get exported as vectors, right click each layer and do: "Create shapes from Vector Layers"), they will be saved to an images folder relative to the destination json folder.
Beware not to overwrite an exiting folder on that same location.


### Performance
This is real time rendering. Although it is pretty optimized, it always helps if you keep your AE project to what is necessary<br/>
More optimizations are on their way, but try not to use huge shapes in AE only to mask a small part of it.<br/>
Too many nodes will also affect performance.

### Help
If you have any animations that don't work or want me to export them, don't hesitate to write. <br/>
I'm really interested in seeing what kind of problems the plugin has. <br/>
my email is **hernantorrisi@gmail.com**


## AE Feature Support
- The script supports precomps, shapes, solids, images, null objects, texts
- It supports masks and inverted masks. Maybe other modes will come but it has a huge performance hit.
- It supports time remapping
- The script supports shapes, rectangles, ellipses and stars.
- Expressions. Check the wiki page for [more info.](https://github.com/airbnb/lottie-web/wiki/Expressions)
- Not supported: image sequences, videos and audio are not supported
- **No  negative layer stretching**! No idea why, but stretching a layer messes with all the data.

## Development
`npm install` or `bower install` first
`npm start`

## Notes
- If you want to modify the parser or the player, there are some gulp commands that can simplify the task
- look at the great animations exported on codepen [See examples on codepen.](http://codepen.io/collection/nVYWZR/)
- gzipping the animation jsons and the player have a huge reduction on the filesize. I recommend doing it if you use it for a project.

## Issues
- For missing mask in Safari browser, please anim.setLocationHref(locationHref) before animation is generated. It usually caused by usage of base tag in html. (see above for description of setLocationHref)
