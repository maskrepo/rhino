package fr.convergence.proddoc.model;

@MaskTable("p_parametre")
public class Parametre {

    private String cle;
    private String valeur;
    private String identifiant;
    private String code_domaine;
    private String code_sous_domaine;
    private String chrono;
    private String type;
    private String type_java;
    private String commentaire;
    private String constante_java;
    private String indic_national;
    private String condition;
    private String nom_table_reference;


    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getCode_domaine() {
        return code_domaine;
    }

    public void setCode_domaine(String code_domaine) {
        this.code_domaine = code_domaine;
    }

    public String getCode_sous_domaine() {
        return code_sous_domaine;
    }

    public void setCode_sous_domaine(String code_sous_domaine) {
        this.code_sous_domaine = code_sous_domaine;
    }

    public String getChrono() {
        return chrono;
    }

    public void setChrono(String chrono) {
        this.chrono = chrono;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_java() {
        return type_java;
    }

    public void setType_java(String type_java) {
        this.type_java = type_java;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getConstante_java() {
        return constante_java;
    }

    public void setConstante_java(String constante_java) {
        this.constante_java = constante_java;
    }

    public String getIndic_national() {
        return indic_national;
    }

    public void setIndic_national(String indic_national) {
        this.indic_national = indic_national;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNom_table_reference() {
        return nom_table_reference;
    }

    public void setNom_table_reference(String nom_table_reference) {
        this.nom_table_reference = nom_table_reference;
    }
}
