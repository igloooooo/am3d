package au.com.iglooit.am3d.vaadin.addons.v3d.client;

import au.com.iglooit.am3d.vaadin.addons.v3d.V3DRender;
import au.com.iglooit.gwtaddon.three4g.client.threejs.cameras.Camera;
import au.com.iglooit.gwtaddon.three4g.client.threejs.cameras.PerspectiveCamera;
import au.com.iglooit.gwtaddon.three4g.client.threejs.core.Geometry;
import au.com.iglooit.gwtaddon.three4g.client.threejs.extras.geometries.CubeGeometry;
import au.com.iglooit.gwtaddon.three4g.client.threejs.materials.Material;
import au.com.iglooit.gwtaddon.three4g.client.threejs.materials.MeshBasicMaterial;
import au.com.iglooit.gwtaddon.three4g.client.threejs.math.Color;
import au.com.iglooit.gwtaddon.three4g.client.threejs.math.Vector3;
import au.com.iglooit.gwtaddon.three4g.client.threejs.objects.Mesh;
import au.com.iglooit.gwtaddon.three4g.client.threejs.renderers.WebGLRenderer;
import au.com.iglooit.gwtaddon.three4g.client.threejs.scenes.Scene;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(V3DRender.class)
public class V3DRenderConnector extends AbstractComponentConnector
{
    private final V3DRenderServerRpc serverRpc = RpcProxy.create(V3DRenderServerRpc.class, this);
    private WebGLRenderer renderer;
    private Scene scene;
    private Camera camera;
    private Mesh mesh;

    public V3DRenderConnector()
    {
        registerRpc(V3DRenderClientRpc.class, new V3DRenderClientRpc()
        {
        });
    }

    @Override
    protected Widget createWidget()
    {
        return new HTMLPanel("");
    }

    @Override
    public HTMLPanel getWidget()
    {
        return (HTMLPanel)super.getWidget();
    }

    @Override
    public V3DRenderState getState()
    {
        return (V3DRenderState)super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent sce)
    {
        super.onStateChanged(sce);
        initPanel();
    }

    private void initPanel()
    {

        Canvas canvas = Canvas.createIfSupported();

        this.renderer = WebGLRenderer.create(canvas);
        this.renderer.setSize(600, 400);
        this.renderer.setClearColor(Color.create(0x000000), 1.0f);

        RootPanel.get().add(canvas);

        Geometry geometry = CubeGeometry.create(100.0, 100.0, 100.0);
        Material material = MeshBasicMaterial.create(0xffffff, true);
        this.mesh = Mesh.create(geometry, material);

        this.scene = Scene.create();
        this.scene.add(this.mesh);

        this.camera = PerspectiveCamera.create(75.0f, 600.0 / 400.0, 1.0f, 1000.0f);
        this.camera.getPosition().setX(250.0);
        this.camera.getPosition().setZ(100.0);
        this.camera.setUp(Vector3.create(0.0, 0.0, 1.0));
        this.camera.lookAt(Vector3.create());
    }

}
