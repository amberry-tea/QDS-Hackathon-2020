module ca.bcit.comp1510.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;    
    
    opens ca.bcitsa.qds2020.team11 to javafx.graphics;
    exports ca.bcitsa.qds2020.team11;
}