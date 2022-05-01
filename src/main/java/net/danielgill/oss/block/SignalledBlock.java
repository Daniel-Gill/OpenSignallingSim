package net.danielgill.oss.block;

import net.danielgill.oss.path.Path;
import net.danielgill.oss.signal.SignalAspect;

public interface SignalledBlock {
    public void updateSignal();
    public void setPath(Path path);
    public SignalAspect getAspect();
    
}
