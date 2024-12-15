# DataDots

DataDots is an Android application that visualizes data as charts using the [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) library. The project follows the MVVM architecture with Hilt for dependency injection, LiveData, and Coroutines for data handling.

---

## Key Features:
- **Chart Visualization**: Displays a chart based on user-provided data points.
- **Chart Saving**: Allows saving the chart to a file (PNG format).
- **Chart Mode Selection**: Choose between linear and smoothed (cubic bezier) chart modes.
- **Data Clearing**: Enables removing all data points from the chart.

---

## Technologies:
- **Kotlin**
- **MVVM (Model-View-ViewModel)**
- **Hilt** (Dependency Injection)
- **LiveData / StateFlow**
- **Coroutines**
- **MPAndroidChart** for chart rendering
- **View Binding** for UI management
- **RecyclerView** for displaying data lists
- **Android Navigation Component**

---

## Usage

### Adding Data Points
Users can input X and Y values, which are added to a list and visualized on the chart.

### Changing Chart Mode
Users can toggle between linear and smoothed chart modes via a dropdown menu.

### Saving the Chart
A file save dialog (ACTION_CREATE_DOCUMENT) is displayed when the save button is pressed. The chart is saved in PNG format.

### Clearing Data
All data points are cleared when navigating away from the fragment.