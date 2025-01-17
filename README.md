# ASCII Art

An ASCII art generator written in Scala

## Compilation

Compile the project with `sbt`, or run the project directly and let `sbt` compile it automatically.

```
$ sbt compile
```

or 

```
$ sbt run
```

## Supported formats

This program supports `png`, `jpg`, `gif` images and random image generation with the `--image-random` argument.

Specify the output destination with `--output-file <file>` or `--output-console`. Both can be used at the same time.

## Filters

### Brightness

Offsets the brightness of each pixel by the specified amount

Usage:
```
$ sbt run --image desired_image.png --brightness 3
$ sbt run--image-random --brightness -5
```

### Scale

Scales the resulting image by the specified factor. Supported values are `0.25`, `1` and `4`.

Usage:
```
$ sbt run --image image-to-load.png --scale 0.25
```

### Invert

Inverts the grayscale value of each pixel.

Usage:
```
$ sbt run --image-random --invert
```

### Flip

Flips the image along the specified axis. Only `x` and `y` axes are supported.

Usage:
```
$ sbt run --image image.png --flip x
$ sbt run --image image.gif --flip y
```

### Rotate

Rotates the image clockwise by the specified amount in degrees. The value has to be a multiple of `90`. 

Usage:
```
$ sbt run --image-random --rotate 90
$ sbt run --image image.jpg --rotate 270
```

### ASCII Conversion

You can specify the conversion table with `--table`. The default is Paul Paul Bourke table. List of predefined tables:
- `linear`: The Paul Bourke table
- `non-linear`: Maps the pixels in a non-linear fashion, frying the image in the process.
- `simple`: An alias this table: ` .:-=+*#%@`

One can define custom linear table:

```
$ ./asciiart --image image_to_load.png --custom-table 0ahoj11:-)
```
