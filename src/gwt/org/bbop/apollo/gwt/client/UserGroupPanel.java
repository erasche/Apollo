package org.bbop.apollo.gwt.client;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.bbop.apollo.gwt.client.demo.DataGenerator;
import org.bbop.apollo.gwt.client.dto.UserGroupInfo;
import org.bbop.apollo.gwt.client.resources.TableResources;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ndunn on 12/17/14.
 */
public class UserGroupPanel extends Composite {
    interface UserGroupBrowserPanelUiBinder extends UiBinder<Widget, UserGroupPanel> {
    }

    private static UserGroupBrowserPanelUiBinder ourUiBinder = GWT.create(UserGroupBrowserPanelUiBinder.class);
    @UiField
    HTML name;

    DataGrid.Resources tablecss = GWT.create(TableResources.TableCss.class);
    @UiField(provided=true) DataGrid<UserGroupInfo> dataGrid = new DataGrid<UserGroupInfo>( 10, tablecss );

    @UiField
    FlexTable trackPermissions;

    public UserGroupPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

        name.setHTML("USDA #1");

        trackPermissions.setHTML(0, 0, "<b>Track</b>");
        trackPermissions.setHTML(0, 1, "<b>Admin</b>");
        trackPermissions.setHTML(0, 2, "<b>Write</b>");
        trackPermissions.setHTML(0, 3, "<b>Export</b>");
        trackPermissions.setHTML(0, 4, "<b>Read</b>");


        String removeString = "<span class=\"glyphicon glyphicon-remove red\" aria-hidden=\"true\"></span>";
        String addString = "<span class=\"glyphicon glyphicon-ok green\" aria-hidden=\"true\"></span>";
        trackPermissions.setHTML(1, 0, "GeneID");
        trackPermissions.setHTML(1, 1, removeString);

        trackPermissions.setHTML(1, 2, addString);
        trackPermissions.setHTML(1, 3, removeString);
        trackPermissions.setHTML(1, 4, addString);

        trackPermissions.setHTML(2, 0, "Fgenesh");
        trackPermissions.setHTML(2, 1, addString);
        trackPermissions.setHTML(2, 2, addString);
        trackPermissions.setHTML(2, 3, removeString);
        trackPermissions.setHTML(2, 4, removeString);


        for (int i = 0; i < 5; i++) {
            trackPermissions.getCellFormatter().addStyleName(0, i, "detail-table-header1");
            if(i>0) trackPermissions.getCellFormatter().addStyleName(0, i, "detail-table-cell");
        }
        for (int i = 1; i < 3; i++) {
            trackPermissions.getCellFormatter().addStyleName(i, 0, "detail-table-header1");
        }


        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 3; j++) {
                trackPermissions.getCellFormatter().addStyleName(j, i, "detail-table-cell");
            }
        }




        TextColumn<UserGroupInfo> firstNameColumn = new TextColumn<UserGroupInfo>() {
            @Override
            public String getValue(UserGroupInfo employee) {
                return employee.getName();
            }
        };
        firstNameColumn.setSortable(true);

        Column<UserGroupInfo, Number> secondNameColumn = new Column<UserGroupInfo, Number>(new NumberCell()) {
            @Override
            public Integer getValue(UserGroupInfo object) {
                return object.getNumberOfUsers();
            }
        };
        secondNameColumn.setSortable(true);

        dataGrid.addColumn(firstNameColumn, "Name");
        dataGrid.addColumn(secondNameColumn, "Users");

        ListDataProvider<UserGroupInfo> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(dataGrid);

        List<UserGroupInfo> trackInfoList = dataProvider.getList();

        for (String user : DataGenerator.getGroups()) {
            trackInfoList.add(new UserGroupInfo(user));
        }

        ColumnSortEvent.ListHandler<UserGroupInfo> sortHandler = new ColumnSortEvent.ListHandler<UserGroupInfo>(trackInfoList);
        dataGrid.addColumnSortHandler(sortHandler);
        sortHandler.setComparator(firstNameColumn, new Comparator<UserGroupInfo>() {
            @Override
            public int compare(UserGroupInfo o1, UserGroupInfo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        sortHandler.setComparator(secondNameColumn, new Comparator<UserGroupInfo>() {
            @Override
            public int compare(UserGroupInfo o1, UserGroupInfo o2) {
                return o1.getNumberOfUsers() - o2.getNumberOfUsers();
            }
        });
    }
}