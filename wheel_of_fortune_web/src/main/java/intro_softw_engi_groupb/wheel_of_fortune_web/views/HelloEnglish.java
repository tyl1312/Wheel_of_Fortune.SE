package intro_softw_engi_groupb.wheel_of_fortune_web.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Hello!")
@Route("/helloen")
public class HelloEnglish extends VerticalLayout {

    public HelloEnglish() {
        super();
        add(new H1("Hello World! 🇬🇧"));

        var buttonHome = new Button("Back to Home Page");
        buttonHome.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        add(buttonHome);
    }
    
}
