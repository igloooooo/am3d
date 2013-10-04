package au.com.iglooit.am3d.vaadin.addons.v3d;

import au.com.iglooit.am3d.vaadin.addons.v3d.client.V3DRenderClientRpc;
import au.com.iglooit.am3d.vaadin.addons.v3d.client.V3DRenderServerRpc;
import au.com.iglooit.am3d.vaadin.addons.v3d.client.V3DRenderState;
import com.vaadin.ui.AbstractComponent;

public class V3DRender extends AbstractComponent
{
    public V3DRender()
    {
        registerRpc(new V3DRenderServerRpc()
        {
            private V3DRenderClientRpc getClientRpc()
            {
                return getRpcProxy(V3DRenderClientRpc.class);
            }
        });
    }

    @Override
    protected V3DRenderState getState()
    {
        return (V3DRenderState)super.getState();
    }
}
