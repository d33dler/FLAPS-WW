/*
 * Copyright 2006-2009, 2017, 2020 United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * The NASA World Wind Java (WWJ) platform is licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * NASA World Wind Java (WWJ) also contains the following 3rd party Open Source
 * software:
 *
 *     Jackson Parser – Licensed under Apache 2.0
 *     GDAL – Licensed under MIT
 *     JOGL – Licensed under  Berkeley Software Distribution (BSD)
 *     Gluegen – Licensed under Berkeley Software Distribution (BSD)
 *
 * A complete listing of 3rd Party software notices and licenses included in
 * NASA World Wind Java (WWJ)  can be found in the WorldWindJava-v2.2 3rd-party
 * notices and licenses PDF found in code directory.
 */
package gov.nasa.worldwindx.examples;

import gov.nasa.worldwind.view.orbit.BasicOrbitView;

import java.awt.*;

/**
 * Example of how to animate the view from one position to another.
 * <p>
 * Use the buttons on the left side of the frame to animate the view: <ul> <li> Zero: Move the view to look at 0 degrees
 * latitude, 0 degrees longitude.</li> <li> Heading: Animate the view from 0 degrees heading to 90 degrees heading.</li>
 * <li> Follow: Animate the view to along a path of geographic positions.</li> <li> Forward: Animate the view to look at
 * the next position in a list.</li> <li> Backward: Animate the view to look at the previous position in a list.</li>
 * </ul>
 *
 * @author tag
 * @version $Id: ViewIteration.java 1171 2013-02-11 21:45:02Z dcollins $
 */

public class FlightSimApp extends ApplicationTemplate {
    protected ApplicationTemplate.AppPanel wwjPanel;
    protected BasicOrbitView orbitView;
    protected Dimension canvasSize = new Dimension(800, 600);

    public ApplicationTemplate.AppPanel init() {
        this.wwjPanel = new ApplicationTemplate.AppPanel(null,this.canvasSize, true);
        this.orbitView = (BasicOrbitView) wwjPanel.getWwd().getView();
        wwjPanel.setPreferredSize(canvasSize);
        return wwjPanel;
    }

    public AppPanel getWwjPanel() {
        return wwjPanel;
    }
}
