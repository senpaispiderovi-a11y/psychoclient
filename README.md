# PSYCHO CLIENT — Fabric 1.21.11

An original client-side Fabric mod project inspired by the compact Feather-style interface. It does not include Feather's proprietary code or assets.

## Current implementation

- Right Shift opens the PSYCHO CLIENT click GUI.
- Black / charcoal panels, white labels and red accent styling.
- Category navigation: All, HUD, PvP, Visual and Performance.
- Scrollable module list, module selection and enable/disable toggles.
- Details panel for the selected module.
- FPS counter HUD toggle.
- XYZ coordinates HUD toggle.

## Planned / placeholder modules

Keystrokes, CPS tracking, Armor HUD, Target HUD, editable HUD positions, crosshair editor, zoom, cosmetics, advanced PvP modules and further performance controls are not implemented in this starter version. They must not be treated as working features yet.

## Build a JAR with GitHub Actions (no local Gradle installation)

1. Create a new repository on GitHub (for example, `psycho-client-fabric`).
2. Upload the **contents** of this project folder to the repository root. Make sure the `.github/workflows/build.yml` file is included.
3. Open the repository's **Actions** tab.
4. Select **Build PSYCHO CLIENT** and click **Run workflow**. A push to `main` or `master` also starts a build automatically.
5. Wait for the workflow to finish with a green check mark.
6. Open the completed workflow run. At the bottom, download the artifact named `PSYCHO-CLIENT-Fabric-1.21.11`.
7. Extract the downloaded artifact ZIP. It contains `psycho-client-0.1.0.jar`.
8. Put that JAR and Fabric API for Minecraft 1.21.11 into your Fabric profile's `mods` folder.

If the Actions run fails, open it and copy the first red error section. The project has not yet been compiled or launched in a Minecraft runtime, so a successful GitHub build still needs an in-game test.

## Local build

Requires Java 21 and Gradle. Run `gradle build`; the JAR is generated in `build/libs/`.
