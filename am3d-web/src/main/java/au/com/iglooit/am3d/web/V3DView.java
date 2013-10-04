package au.com.iglooit.am3d.web;

import au.com.iglooit.am3d.web.data.Generator;
import au.com.iglooit.vaadin.addon.g3d.MyTestComp;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class V3DView extends VerticalLayout implements View
{
    private Window notifications;

    public V3DView()
    {
        setSizeFull();
        addStyleName("dashboard-view");

        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("My Dashboard");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        Button notify = new Button("2");
        notify.setDescription("Notifications (2 unread)");
        // notify.addStyleName("borderless");
        notify.addStyleName("notifications");
        notify.addStyleName("unread");
        notify.addStyleName("icon-only");
        notify.addStyleName("icon-bell");
        notify.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                ((DashboardUI)getUI()).clearDashboardButtonBadge();
                event.getButton().removeStyleName("unread");
                event.getButton().setDescription("Notifications");

                if (notifications != null && notifications.getUI() != null)
                    notifications.close();
                else
                {
                    buildNotifications(event);
                    getUI().addWindow(notifications);
                    notifications.focus();
                    ((CssLayout)getUI().getContent())
                        .addLayoutClickListener(new LayoutEvents.LayoutClickListener()
                        {
                            @Override
                            public void layoutClick(LayoutEvents.LayoutClickEvent event)
                            {
                                notifications.close();
                                ((CssLayout)getUI().getContent())
                                    .removeLayoutClickListener(this);
                            }
                        });
                }

            }
        });
        top.addComponent(notify);
        top.setComponentAlignment(notify, Alignment.MIDDLE_LEFT);

        Button edit = new Button();
        edit.addStyleName("icon-edit");
        edit.addStyleName("icon-only");
        top.addComponent(edit);
        edit.setDescription("Edit Dashboard");
        edit.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                final Window w = new Window("Edit Dashboard");

                w.setModal(true);
                w.setClosable(false);
                w.setResizable(false);
                w.addStyleName("edit-dashboard");

                getUI().addWindow(w);

                w.setContent(new VerticalLayout()
                {
                    TextField name = new TextField("Dashboard Name");

                    {
                        addComponent(new FormLayout()
                        {
                            {
                                setSizeUndefined();
                                setMargin(true);
                                name.setValue(title.getValue());
                                addComponent(name);
                                name.focus();
                                name.selectAll();
                            }
                        });

                        addComponent(new HorizontalLayout()
                        {
                            {
                                setMargin(true);
                                setSpacing(true);
                                addStyleName("footer");
                                setWidth("100%");

                                Button cancel = new Button("Cancel");
                                cancel.addClickListener(new Button.ClickListener()
                                {
                                    @Override
                                    public void buttonClick(Button.ClickEvent event)
                                    {
                                        w.close();
                                    }
                                });
                                cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);
                                addComponent(cancel);
                                setExpandRatio(cancel, 1);
                                setComponentAlignment(cancel,
                                    Alignment.TOP_RIGHT);

                                Button ok = new Button("Save");
                                ok.addStyleName("wide");
                                ok.addStyleName("default");
                                ok.addClickListener(new Button.ClickListener()
                                {
                                    @Override
                                    public void buttonClick(Button.ClickEvent event)
                                    {
                                        title.setValue(name.getValue());
                                        w.close();
                                    }
                                });
                                ok.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);
                                addComponent(ok);
                            }
                        });

                    }
                });

            }
        });
        top.setComponentAlignment(edit, Alignment.MIDDLE_LEFT);

        HorizontalLayout row = new HorizontalLayout();
        row.setMargin(true);
        row.setSizeFull();
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 2);

        row.addComponent(createPanel(createG3d()));


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {

    }

    private CssLayout createG3d()
    {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        MyTestComp v3dRender = new MyTestComp();
        panel.addComponent(v3dRender);
        return panel;
    }

    private CssLayout createPanel(Component content)
    {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        Button configure = new Button();
        configure.addStyleName("configure");
        configure.addStyleName("icon-cog");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription("Configure");
        configure.addStyleName("small");
        configure.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                Notification.show("Not implemented in this demo");
            }
        });
        panel.addComponent(configure);

        panel.addComponent(content);
        return panel;
    }

    private void buildNotifications(Button.ClickEvent event)
    {
        notifications = new Window("Notifications");
        VerticalLayout l = new VerticalLayout();
        l.setMargin(true);
        l.setSpacing(true);
        notifications.setContent(l);
        notifications.setWidth("300px");
        notifications.addStyleName("notifications");
        notifications.setClosable(false);
        notifications.setResizable(false);
        notifications.setDraggable(false);
        notifications.setPositionX(event.getClientX() - event.getRelativeX());
        notifications.setPositionY(event.getClientY() - event.getRelativeY());
        notifications.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Label label = new Label(
            "<hr><b>"
                + Generator.randomFirstName()
                + " "
                + Generator.randomLastName()
                + " created a new report</b><br><span>25 minutes ago</span><br>"
                + Generator.randomText(18), ContentMode.HTML);
        l.addComponent(label);

        label = new Label("<hr><b>" + Generator.randomFirstName() + " "
            + Generator.randomLastName()
            + " changed the schedule</b><br><span>2 days ago</span><br>"
            + Generator.randomText(10), ContentMode.HTML);
        l.addComponent(label);
    }
}
