Names - Jeffrey Gu, Jordan Shapiro, Amik Mandal

## Commonalities between the simulation exercises

- They all had cells that had a varied condition, usually some sort of positive, neutral, and negative condition.
- The behavior of the cells are partly dependent on their neighboring cells.
- In some implementations, probability is a factor in deciding a cell's fate for the next generation.

## High Level Design

We discussed the potential classes and abstractions we will make. Here is the list we came up with:
- GUI (front-end)
- Rules
- Driver (Controller)
- backend.Grid (back-end)
- backend.Cell (abstract)
- FileLoader

GUI interacts with Rules, FileLoader, Driver, and backend.Cell, and Driver relays these middle-ground parameters to the grid,
which will be the back-end of the simulation. 

We then started discussing specific methods/responsibilities for each class.