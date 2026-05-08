# Layout definition format

The layout is defined in WYSIWYG style in a text string (or file).

You can only make rectangles, inside one big rectangle.
The rectangles must be disjoint, they can not be nested.

In each rectangle, you can put a name (a letter, followed by letters or numbers).
If you don't put a name inside a rectangle, it will be considered space
(it will be assigned a name "_space" followed by a sequential number).
Space rectangles will be associated with a JLabel(" ") component.

If certain flags are encountered, a mode is set :

- ^ : fill vertically
- &lt; or &gt; : fill horizontally

If the name touches a boundary, the anchor will be set accordingly (N, NE, E, SE, S, SW, W, NW).


Example :

    +-----------------+--------+
    |        A        |       B|
    +-----------------+--------+
    |                          |
    |             C            |
    |                          |
    +--------------------------+

This would translate to a grid of 

4 lines x 26 columns.
The rightmost column doesn't count as a separator.


Inside this grid, you have 3 boxes :

- Box A : 1 line x 17 columns
- Box B : 1 line x 9 columns
- Box C : 3 lines x 27 columns

The  anchors :

- A will have an anchor on North and South.
- B will have an anchor on North, East and South.
- C will have no anchors.

A cell must always be rectangular, but can be made from joined cells, just like in the GridBagLayout.

Examples of accepted layout :


    +-----+-----+-----+-------+
    |     |     |     |       |
    |  A  |  D  |  E  |   G   |
    |     |     |     |       |
    +-----+-----+-----+-------+
    |     |     |     |       |
    |  B  |  C  |  F  |       |
    |     |     |     |       |
    +-----+-----+-----+       |
    |I          |     |       |
    |           |J    |   H   |
    |           |     |       |
    |           +-----+       |
    |           |     |       |
    |           |K    |       |
    |           |     |       |
    +-----------+-----+-------+
    |                         |
    |                         |
    |                         |
    |                         |
    |                         |
    |                         |
    |            L            |
    +-------------------------+

Examples of forbidden layout (non-rectangular zones, nested
rectangles) :

    +-----------+-----+-------+
    |           |     |       |
    |  A        |  B  |   C   |
    |           |     |       |
    |     +-----+-----+-------+
    |     |D                  |
    |     |                   |
    |     |                   |
    +-----+     +-----+       |
    |     |     |     |       |
    |  E  |     |  F  |       |
    |     |     |     |       |
    +-----+     +-----+       |
    |     |                   |
    |  G  |                   |
    |     |                   |
    +-----+-------------------+


Also the parser is not too picky about layout specs.

In fact internally all "|" and "-" are replaced by "+" chars.

So the spec listed above is equivalent to :

    ++++++++++++++++++++++++++++
    +        A        +       B+
    ++++++++++++++++++++++++++++
    +                          +
    +             C            +
    +                          +
    ++++++++++++++++++++++++++++

If any chars '&lt;' or '&gt;' are found, the component will fill horizontally.

If any char '^' is found, the component will fill vertically.

Each width and height will be used directly in the `GridBagConstraint` objects,
so that the relative widths and heights in the drawing will be preserved in
the layout.

The additional parameters of the GridBagLayout can be set programmatically once the
drawing has been parsed and each rectangle has its name :

- weightx (use a value between 0 and 1.0, and make sure that the total sum of the weights is 1.0)
- weighty (use a value between 0 and 1.0, and make sure that the total sum of the weights is 1.0)
- insets
- ipadx
- ipady



