package net.danielgill.ros.block;

import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.SignalAspect;

public interface SignalledBlock {
    public void updateSignal();
    public void setPath(Path path);
    public SignalAspect getAspect();
    
}
