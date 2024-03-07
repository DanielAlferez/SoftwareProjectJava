/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.poo;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.factories.ConnectionDatabase;
import com.mycompany.poo.factories.FactoryDB;
import com.mycompany.poo.factories.IRepositoryFactory;
import com.mycompany.poo.repositories.h2.DepartamentoRepositoryH2;
import com.mycompany.poo.repositories.h2.EstudianteRepositoryH2;
import com.mycompany.poo.repositories.h2.LugarRepositoryH2;
import com.mycompany.poo.repositories.h2.MunicipioRepositoryH2;
import com.mycompany.poo.repositories.h2.ProgramaRepositoryH2;
import com.mycompany.poo.repositories.interfaces.IRepository;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andres
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    FactoryDB factory = FactoryDB.getInstance();
    IRepositoryFactory dbFactory = factory.getDatabase();
        
    IRepository<Departamento> departamentoRepository = dbFactory.createDepartamentoRepository();
    IRepository<Municipio> municipioRepository = dbFactory.createMunicipioRepository();
    IRepository<Lugar> lugarRepository = dbFactory.createLugarRepository();
    IRepository<Programa> programaRepository = dbFactory.createProgramaRepository();
    IRepository<Estudiante> estudianteRepository = dbFactory.createEstudianteRepository();
    
    DefaultTableModel modeloD;
    DefaultTableModel modeloM;
    DefaultTableModel modeloL;
    DefaultTableModel modeloP;
    DefaultTableModel modeloE;
    int idDepartamento = 0;
    int idMunicipio = 0;
    String direccion = "";
    int idPrograma = 0;
    int idEstudiante = 0;
    public GUI() {
        initComponents();
        setLocationRelativeTo(null);
        
        //Departamentos
        List<Departamento> departamentos = departamentoRepository.read();
        listarDepartamentos(departamentos);
        
        //Municipios
        List<Municipio> municipios = municipioRepository.read();
        listarMunicipios(municipios);
        comboBoxDepartamentos(departamentos, comboDepartamentos);
        
        //Lugares
        List<Lugar> lugares = lugarRepository.read();
        listarLugares(lugares);
        comboBoxDepartamentos(departamentos, comboDepartamentos2);
        comboBoxMunicipios(municipios, comboMunicipios);
        
        //Programas
        List<Programa> programas = programaRepository.read();
        listarProgramas(programas);
        comboBoxLugares(lugares, comboDirecciones);
        
        //Estudiantes
        List<Estudiante> estudiantes = estudianteRepository.read();
        listarEstudiantes(estudiantes);
        comboBoxProgramas(programas, comboProgramas);
        comboBoxLugares(lugares, comboDirecciones2);
    }
    
    
    public void limpiarComboBox(JComboBox comboBox) {
    comboBox.removeAllItems();
    }
    
    //DEPARTAMENTOS 
    public void listarDepartamentos(List<Departamento> departamentos){
        modeloD = (DefaultTableModel)tablaDepartamentos.getModel();
        // Llenar el modelo de tabla con los datos de los departamentos
        for (Departamento departamento : departamentos) {
            modeloD.addRow(new Object[]{departamento.getId_departamento(), departamento.getNombre_departamento()});
        }
        tablaDepartamentos.setModel(modeloD);
    }

    public void agregarDepartamento() {
        int IdDepartamento =  Integer.parseInt(FIdDepartamento.getText());
        String nombreDepartamento = FNombreDepartamento.getText();
        departamentoRepository.create(new Departamento(IdDepartamento,nombreDepartamento));
        limpiarTablaD();
        
    }
    
    public void modificarDepartamento(){
        //idDepartamento = Integer.parseInt(FIdDepartamento.getText());
        String nombreDepartamento =  FNombreDepartamento.getText();
        if (!nombreDepartamento.isEmpty()) {
            departamentoRepository.update(new Departamento(idDepartamento,nombreDepartamento));
            JOptionPane.showMessageDialog(null, "Departamento actualizado!!!");
            limpiarTablaD();
        }else {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del departamento.");
        limpiarTablaD();
    }
    }
    
    public void eliminarDepartamento() throws SQLException{
        int fila = tablaDepartamentos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null,"Usuario no Seleccionado");
        } else {
            Departamento departamento = DepartamentoRepositoryH2.obtenerDepartamentoPorId(ConnectionDatabase.getConnection(), idDepartamento);
            departamentoRepository.delete(departamento);
            JOptionPane.showMessageDialog(null, "Departamento Eliminado");
            limpiarTablaD();
        }
    }
    
    public void nuevoDepartamento(){
        FIdDepartamento.setText("");
        FNombreDepartamento.setText("");
        FIdDepartamento.setEditable(true);
        FIdDepartamento.setEnabled(true);
    }
    
    public void limpiarTablaD(){
        for(int i=0; i<tablaDepartamentos.getRowCount();i++){
            modeloD.removeRow(i);
            i=i-1;
        }
    }
    
    //MUNICIPIOS
     public void comboBoxDepartamentos(List<Departamento> departamentos,JComboBox combobox){
         limpiarComboBox(combobox);
         for (Departamento departamento : departamentos) {
            combobox.addItem(departamento.getNombre_departamento());
        }
     }
     
     public void comboBoxMunicipios(List<Municipio> municipios,JComboBox combobox){
         limpiarComboBox(combobox);
         for (Municipio municipio : municipios) {
            combobox.addItem(municipio.getNombre_municipio());
        }
     }
     
     public void comboBoxProgramas(List<Programa> programas,JComboBox combobox){
         limpiarComboBox(combobox);
         for (Programa programa : programas) {
            combobox.addItem(programa.getNombre());
        }
     }
     
     public void listarMunicipios(List<Municipio> municipios){
        modeloM = (DefaultTableModel)tablaMunicipios.getModel();
        // Llenar el modelo de tabla con los datos de los departamentos
        for (Municipio municipio : municipios) {
            modeloM.addRow(new Object[]{municipio.getId_municipio(), municipio.getNombre_municipio(), municipio.getDepartamento().getNombre_departamento()});
        }
        tablaMunicipios.setModel(modeloM);
    }
     
    public void agregarMunicipio() {
        int IdMunicipio =  Integer.parseInt(FIdMunicipio.getText());
        String nombreMunicipio = FNombreMunicipio.getText();
        Departamento departamento = departamentoRepository.read().get(comboDepartamentos.getSelectedIndex());
        municipioRepository.create(new Municipio(IdMunicipio,nombreMunicipio,departamento));
        limpiarTablaM();
    }
    
    public void modificarMunicipio(){
        
        String nombreMunicipio =  FNombreMunicipio.getText();
        Departamento departamento = departamentoRepository.read().get(comboDepartamentos.getSelectedIndex());
        if (!nombreMunicipio.isEmpty()) {
            municipioRepository.update(new Municipio(idMunicipio,nombreMunicipio,departamento));
            JOptionPane.showMessageDialog(null, "Municipio actualizado!!!");
            limpiarTablaM();
        }else {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del municipio.");
        limpiarTablaM();
    }
    }
    
    public void eliminarMunicipio() throws SQLException{
        int fila = tablaMunicipios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null,"Usuario no Seleccionado");
        } else {
            Municipio municipio = MunicipioRepositoryH2.obtenerMunicipioPorId(ConnectionDatabase.getConnection(), idMunicipio);
            municipioRepository.delete(municipio);
            JOptionPane.showMessageDialog(null, "Municipio Eliminado");
            limpiarTablaM();
        }
    }
    
    public void nuevoMunicipio(){
        FIdMunicipio.setText("");
        FNombreMunicipio.setText("");
        FIdMunicipio.setEditable(true);
        FIdMunicipio.setEnabled(true);
    }
    
    public void limpiarTablaM(){
        for(int i=0; i<tablaMunicipios.getRowCount();i++){
            modeloM.removeRow(i);
            i=i-1;
        }
    }
    
    //LUGARES
    
    public void listarLugares(List<Lugar> lugares){
        modeloL = (DefaultTableModel)tablaLugares.getModel();
        // Llenar el modelo de tabla con los datos de los departamentos
        for (Lugar lugar : lugares) {
            modeloL.addRow(new Object[]{lugar.getDireccion() , lugar.getDepartamento().getNombre_departamento(), lugar.getMunicipio().getNombre_municipio()});
        }
        tablaLugares.setModel(modeloL);
    }
    
    public void agregarLugar() {
        String direccion =  FDireccion.getText();
        Departamento departamento = departamentoRepository.read().get(comboDepartamentos2.getSelectedIndex());
        Municipio municipio = municipioRepository.read().get(comboMunicipios.getSelectedIndex());
        lugarRepository.create(new Lugar(direccion,departamento,municipio));
        limpiarTablaL();
    }
        
    public void modificarLugar(){
        Departamento departamento = departamentoRepository.read().get(comboDepartamentos2.getSelectedIndex());
        Municipio municipio = municipioRepository.read().get(comboMunicipios.getSelectedIndex());
        lugarRepository.update(new Lugar(direccion,departamento, municipio));
        JOptionPane.showMessageDialog(null, "Lugar actualizado!!!");
        limpiarTablaL();
    }
    
    public void eliminarLugar() throws SQLException{
        int fila = tablaLugares.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null,"Usuario no Seleccionado");
        } else {
            Lugar lugar = LugarRepositoryH2.obtenerLugarPorDireccion(ConnectionDatabase.getConnection(), direccion);
            lugarRepository.delete(lugar);
            JOptionPane.showMessageDialog(null, "Lugar Eliminado");
            limpiarTablaL();
        }
    }
    
    public void nuevoLugar(){
        FDireccion.setText("");
        FDireccion.setEditable(true);
        FDireccion.setEnabled(true);
    }
    
    public void limpiarTablaL(){
        for(int i=0; i<tablaLugares.getRowCount();i++){
            modeloL.removeRow(i);
            i=i-1;
        }
    }
    
    
    //PROGRAMAS
    public void comboBoxLugares(List<Lugar> lugares,JComboBox combobox){
        limpiarComboBox(combobox);
         for (Lugar lugar : lugares) {
            combobox.addItem(lugar.getDireccion());
        }
     }
    
    public void listarProgramas(List<Programa> programas){
        modeloP = (DefaultTableModel)tablaProgramas.getModel();
        // Llenar el modelo de tabla con los datos de los departamentos
        for (Programa programa : programas) {
            modeloP.addRow(new Object[]{programa.getId_programa(),programa.getNombre(), programa.getSemestre(), programa.getLugar().getDireccion()});
        }
        tablaProgramas.setModel(modeloP);
    }
     
    public void agregarPrograma() {
        int idPrograma =  Integer.parseInt(FIdPrograma.getText());
        String nombrePrograma = FNombrePrograma.getText();
        int semestre = Integer.parseInt(FSemestre.getText());
        Lugar lugar = lugarRepository.read().get(comboDirecciones.getSelectedIndex());
        programaRepository.create(new Programa(idPrograma,nombrePrograma,semestre,lugar));
        limpiarTablaP();
    }
    
    public void modificarPrograma(){
        
        String nombrePrograma =  FNombrePrograma.getText();
        int semestre = Integer.parseInt(FSemestre.getText());
        Lugar lugar = lugarRepository.read().get(comboDirecciones.getSelectedIndex());
        programaRepository.update(new Programa(idPrograma,nombrePrograma,semestre,lugar));
        JOptionPane.showMessageDialog(null, "Programa actualizado!!!");
        limpiarTablaM();
    }
    
    public void eliminarPrograma() throws SQLException{
        int fila = tablaProgramas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null,"Usuario no Seleccionado");
        } else {
            Programa programa = ProgramaRepositoryH2.obtenerProgramaPorId(ConnectionDatabase.getConnection(), idPrograma);
            programaRepository.delete(programa);
            JOptionPane.showMessageDialog(null, "Programa Eliminado");
            limpiarTablaP();
        }
    }
    
    public void nuevoPrograma(){
        FIdPrograma.setText("");
        FNombrePrograma.setText("");
        FSemestre.setText("");
        FIdPrograma.setEditable(true);
        FIdPrograma.setEnabled(true);
    }
    
    public void limpiarTablaP(){
        for(int i=0; i<tablaProgramas.getRowCount();i++){
            modeloP.removeRow(i);
            i=i-1;
        }
    }
    
    //ESTUDIANTES
    
    public void listarEstudiantes(List<Estudiante> estudiantes){
        modeloE = (DefaultTableModel)tablaEstudiantes.getModel();
        // Llenar el modelo de tabla con los datos de los departamentos
        for (Estudiante estudiante : estudiantes) {
            modeloE.addRow(new Object[]{estudiante.getId(),estudiante.getNombre(),estudiante.getApellido(),estudiante.getPrograma(),estudiante.getDireccion()});
        }
        tablaEstudiantes.setModel(modeloE);
    }
    
    public void agregarEstudiante() {
        int idEstudiante = Integer.parseInt(FIdEstudiante.getText());
        String nombre =  FNombreEstudiante.getText();
        String apellido = FApellidoEstudiante.getText();
        double codigo = Double.parseDouble(FCodigo.getText());
        Programa programa = programaRepository.read().get(comboProgramas.getSelectedIndex());
        Lugar direccion = lugarRepository.read().get(comboDirecciones2.getSelectedIndex());
        estudianteRepository.create(new Estudiante(idEstudiante,nombre,apellido,codigo,programa,direccion));
        limpiarTablaE();
    }
        
    public void modificarEstudiante(){
        String nombre =  FNombreEstudiante.getText();
        String apellido = FApellidoEstudiante.getText();
        double codigo = Double.parseDouble(FCodigo.getText());
        Programa programa = programaRepository.read().get(comboProgramas.getSelectedIndex());
        Lugar direccion = lugarRepository.read().get(comboDirecciones2.getSelectedIndex());
        estudianteRepository.update(new Estudiante(idEstudiante,nombre,apellido,codigo,programa,direccion));
        JOptionPane.showMessageDialog(null, "Estudiante actualizado!!!");
        limpiarTablaE();
    }
    
    public void eliminarEstudiante() throws SQLException{
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null,"Usuario no Seleccionado");
        } else {
            Estudiante estudiante = EstudianteRepositoryH2.buscarEstudiantePorId(ConnectionDatabase.getConnection(), idEstudiante);
            estudianteRepository.delete(estudiante);
            JOptionPane.showMessageDialog(null, "Estudiante Eliminado");
            limpiarTablaE();
        }
    }
    
    public void nuevoEstudiante(){
        FIdEstudiante.setText("");
        FNombreEstudiante.setText("");
        FApellidoEstudiante.setText("");
        FCodigo.setText("");
        FIdEstudiante.setEditable(true);
        FIdEstudiante.setEnabled(true);
    }
    
    public void limpiarTablaE(){
        for(int i=0; i<tablaEstudiantes.getRowCount();i++){
            modeloE.removeRow(i);
            i=i-1;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnDepartamentos = new javax.swing.JPanel();
        txtdep = new javax.swing.JLabel();
        btnMunicipios = new javax.swing.JPanel();
        txtmun = new javax.swing.JLabel();
        btnLugares = new javax.swing.JPanel();
        txtdir = new javax.swing.JLabel();
        btnProgramas = new javax.swing.JPanel();
        txtpro = new javax.swing.JLabel();
        btnEstudiantes = new javax.swing.JPanel();
        txtest = new javax.swing.JLabel();
        panel = new javax.swing.JTabbedPane();
        panelDepartamentos = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        FIdDepartamento = new javax.swing.JTextField();
        FNombreDepartamento = new javax.swing.JTextField();
        AgregarDepartamento = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ModificarDepartamento = new javax.swing.JButton();
        EliminarDepartamento = new javax.swing.JButton();
        NuevoDepartamento = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDepartamentos = new javax.swing.JTable();
        panelMunicipios = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMunicipios = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        FIdMunicipio = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        FNombreMunicipio = new javax.swing.JTextField();
        comboDepartamentos = new javax.swing.JComboBox<>();
        AgregarMunicipio = new javax.swing.JButton();
        ModificarMunicipio = new javax.swing.JButton();
        EliminarMunicipio = new javax.swing.JButton();
        NuevoMunicipio = new javax.swing.JButton();
        panelLugares = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaLugares = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        FDireccion = new javax.swing.JTextField();
        comboDepartamentos2 = new javax.swing.JComboBox<>();
        comboMunicipios = new javax.swing.JComboBox<>();
        AgregarLugar = new javax.swing.JButton();
        ModificarLugar = new javax.swing.JButton();
        EliminarLugar = new javax.swing.JButton();
        NuevoLugar = new javax.swing.JButton();
        panelProgramas = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        FIdPrograma = new javax.swing.JTextField();
        FNombrePrograma = new javax.swing.JTextField();
        FSemestre = new javax.swing.JTextField();
        comboDirecciones = new javax.swing.JComboBox<>();
        AgregarPrograma = new javax.swing.JButton();
        ModificarPrograma = new javax.swing.JButton();
        EliminarPrograma = new javax.swing.JButton();
        NuevoPrograma = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProgramas = new javax.swing.JTable();
        panelEstudiantes = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        FIdEstudiante = new javax.swing.JTextField();
        FNombreEstudiante = new javax.swing.JTextField();
        FApellidoEstudiante = new javax.swing.JTextField();
        FCodigo = new javax.swing.JTextField();
        comboProgramas = new javax.swing.JComboBox<>();
        comboDirecciones2 = new javax.swing.JComboBox<>();
        AgregarEstudiante = new javax.swing.JButton();
        ModificarEstudiante = new javax.swing.JButton();
        EliminarEstudiante = new javax.swing.JButton();
        NuevoEstudiante = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));

        btnDepartamentos.setBackground(new java.awt.Color(51, 102, 255));
        btnDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDepartamentosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDepartamentosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDepartamentosMouseExited(evt);
            }
        });

        txtdep.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtdep.setForeground(new java.awt.Color(255, 255, 255));
        txtdep.setText("Departamentos   >");

        javax.swing.GroupLayout btnDepartamentosLayout = new javax.swing.GroupLayout(btnDepartamentos);
        btnDepartamentos.setLayout(btnDepartamentosLayout);
        btnDepartamentosLayout.setHorizontalGroup(
            btnDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDepartamentosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(txtdep)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnDepartamentosLayout.setVerticalGroup(
            btnDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDepartamentosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtdep, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnMunicipios.setBackground(new java.awt.Color(51, 102, 255));
        btnMunicipios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMunicipiosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMunicipiosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMunicipiosMouseExited(evt);
            }
        });

        txtmun.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtmun.setForeground(new java.awt.Color(255, 255, 255));
        txtmun.setText("Municipios           >");

        javax.swing.GroupLayout btnMunicipiosLayout = new javax.swing.GroupLayout(btnMunicipios);
        btnMunicipios.setLayout(btnMunicipiosLayout);
        btnMunicipiosLayout.setHorizontalGroup(
            btnMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMunicipiosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(txtmun, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        btnMunicipiosLayout.setVerticalGroup(
            btnMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnMunicipiosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtmun, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnLugares.setBackground(new java.awt.Color(51, 102, 255));
        btnLugares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLugaresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLugaresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLugaresMouseExited(evt);
            }
        });

        txtdir.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtdir.setForeground(new java.awt.Color(255, 255, 255));
        txtdir.setText("Lugares               >");

        javax.swing.GroupLayout btnLugaresLayout = new javax.swing.GroupLayout(btnLugares);
        btnLugares.setLayout(btnLugaresLayout);
        btnLugaresLayout.setHorizontalGroup(
            btnLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLugaresLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLugaresLayout.setVerticalGroup(
            btnLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnLugaresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnProgramas.setBackground(new java.awt.Color(51, 102, 255));
        btnProgramas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProgramasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProgramasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProgramasMouseExited(evt);
            }
        });

        txtpro.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtpro.setForeground(new java.awt.Color(255, 255, 255));
        txtpro.setText("Programas          >");

        javax.swing.GroupLayout btnProgramasLayout = new javax.swing.GroupLayout(btnProgramas);
        btnProgramas.setLayout(btnProgramasLayout);
        btnProgramasLayout.setHorizontalGroup(
            btnProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProgramasLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(txtpro, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnProgramasLayout.setVerticalGroup(
            btnProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnProgramasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtpro, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnEstudiantes.setBackground(new java.awt.Color(51, 102, 255));
        btnEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEstudiantesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstudiantesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstudiantesMouseExited(evt);
            }
        });

        txtest.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtest.setForeground(new java.awt.Color(255, 255, 255));
        txtest.setText("Estudiantes        >");

        javax.swing.GroupLayout btnEstudiantesLayout = new javax.swing.GroupLayout(btnEstudiantes);
        btnEstudiantes.setLayout(btnEstudiantesLayout);
        btnEstudiantesLayout.setHorizontalGroup(
            btnEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEstudiantesLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(txtest)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnEstudiantesLayout.setVerticalGroup(
            btnEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEstudiantesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtest, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDepartamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMunicipios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLugares, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProgramas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEstudiantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnLugares, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnProgramas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        panel.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        panel.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        panelDepartamentos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Departamentos");

        FIdDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FIdDepartamentoActionPerformed(evt);
            }
        });

        AgregarDepartamento.setText("Agregar");
        AgregarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarDepartamentoActionPerformed(evt);
            }
        });

        jLabel6.setText("ID");

        jLabel12.setText("Nombre");

        ModificarDepartamento.setText("Modificar");
        ModificarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarDepartamentoActionPerformed(evt);
            }
        });

        EliminarDepartamento.setText("Eliminar");
        EliminarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarDepartamentoActionPerformed(evt);
            }
        });

        NuevoDepartamento.setText("Nuevo");
        NuevoDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoDepartamentoActionPerformed(evt);
            }
        });

        tablaDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDepartamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDepartamentos);

        javax.swing.GroupLayout panelDepartamentosLayout = new javax.swing.GroupLayout(panelDepartamentos);
        panelDepartamentos.setLayout(panelDepartamentosLayout);
        panelDepartamentosLayout.setHorizontalGroup(
            panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelDepartamentosLayout.createSequentialGroup()
                        .addComponent(AgregarDepartamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel6))
                                .addGap(42, 42, 42)
                                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7)
                                    .addComponent(FIdDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                    .addComponent(FNombreDepartamento)))
                            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(ModificarDepartamento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(EliminarDepartamento)))
                        .addGap(51, 51, 51)
                        .addComponent(NuevoDepartamento))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        panelDepartamentosLayout.setVerticalGroup(
            panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDepartamentosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel7)
                .addGap(32, 32, 32)
                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FIdDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FNombreDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(panelDepartamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarDepartamento)
                    .addComponent(ModificarDepartamento)
                    .addComponent(EliminarDepartamento)
                    .addComponent(NuevoDepartamento))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        panel.addTab("tab1", panelDepartamentos);

        panelMunicipios.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Municipios");

        tablaMunicipios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Departamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaMunicipios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMunicipiosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaMunicipios);
        if (tablaMunicipios.getColumnModel().getColumnCount() > 0) {
            tablaMunicipios.getColumnModel().getColumn(0).setMinWidth(100);
            tablaMunicipios.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaMunicipios.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jLabel13.setText("ID:");

        FIdMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FIdMunicipioActionPerformed(evt);
            }
        });

        jLabel14.setText("Nombre:");

        jLabel15.setText("Departamento:");

        FNombreMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FNombreMunicipioActionPerformed(evt);
            }
        });

        comboDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboDepartamentosMouseClicked(evt);
            }
        });

        AgregarMunicipio.setText("Agregar");
        AgregarMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarMunicipioActionPerformed(evt);
            }
        });

        ModificarMunicipio.setText("Modificar");
        ModificarMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarMunicipioActionPerformed(evt);
            }
        });

        EliminarMunicipio.setText("Eliminar");
        EliminarMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarMunicipioActionPerformed(evt);
            }
        });

        NuevoMunicipio.setText("Nuevo");
        NuevoMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoMunicipioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMunicipiosLayout = new javax.swing.GroupLayout(panelMunicipios);
        panelMunicipios.setLayout(panelMunicipiosLayout);
        panelMunicipiosLayout.setHorizontalGroup(
            panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMunicipiosLayout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMunicipiosLayout.createSequentialGroup()
                        .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMunicipiosLayout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMunicipiosLayout.createSequentialGroup()
                                        .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel13))
                                        .addGap(45, 45, 45)
                                        .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(FNombreMunicipio, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(FIdMunicipio, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboDepartamentos, 0, 102, Short.MAX_VALUE)))
                                    .addGroup(panelMunicipiosLayout.createSequentialGroup()
                                        .addComponent(ModificarMunicipio)
                                        .addGap(60, 60, 60)
                                        .addComponent(EliminarMunicipio))))
                            .addComponent(AgregarMunicipio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NuevoMunicipio)))
                .addGap(35, 35, 35))
            .addGroup(panelMunicipiosLayout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMunicipiosLayout.setVerticalGroup(
            panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMunicipiosLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(FIdMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(FNombreMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(comboDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(panelMunicipiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarMunicipio)
                    .addComponent(ModificarMunicipio)
                    .addComponent(EliminarMunicipio)
                    .addComponent(NuevoMunicipio))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        panel.addTab("tab2", panelMunicipios);

        panelLugares.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Lugares");

        tablaLugares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dirección", "Municipio", "Departamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaLugares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaLugaresMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaLugares);

        jLabel16.setText("Dirección:");

        jLabel17.setText("Departamento:");

        jLabel18.setText("Municipio:");

        FDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FDireccionActionPerformed(evt);
            }
        });

        comboDepartamentos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboDepartamentos2MouseClicked(evt);
            }
        });

        comboMunicipios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboMunicipiosMouseClicked(evt);
            }
        });

        AgregarLugar.setText("Agregar");
        AgregarLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarLugarActionPerformed(evt);
            }
        });

        ModificarLugar.setText("Modificar");
        ModificarLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarLugarActionPerformed(evt);
            }
        });

        EliminarLugar.setText("Eliminar");
        EliminarLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarLugarActionPerformed(evt);
            }
        });

        NuevoLugar.setText("Nuevo");
        NuevoLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoLugarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLugaresLayout = new javax.swing.GroupLayout(panelLugares);
        panelLugares.setLayout(panelLugaresLayout);
        panelLugaresLayout.setHorizontalGroup(
            panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLugaresLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLugaresLayout.createSequentialGroup()
                        .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FDireccion)
                            .addComponent(comboDepartamentos2, 0, 129, Short.MAX_VALUE)
                            .addComponent(comboMunicipios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelLugaresLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(ModificarLugar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLugaresLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLugaresLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(246, 246, 246))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLugaresLayout.createSequentialGroup()
                        .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelLugaresLayout.createSequentialGroup()
                                .addComponent(AgregarLugar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EliminarLugar)
                                .addGap(40, 40, 40)
                                .addComponent(NuevoLugar))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))))
        );
        panelLugaresLayout.setVerticalGroup(
            panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLugaresLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel9)
                .addGap(14, 14, 14)
                .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(FDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(comboDepartamentos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(comboMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(panelLugaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NuevoLugar)
                    .addComponent(EliminarLugar)
                    .addComponent(ModificarLugar)
                    .addComponent(AgregarLugar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        panel.addTab("tab3", panelLugares);

        panelProgramas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("Programas");

        jLabel19.setText("Id:");

        jLabel20.setText("Nombre:");

        jLabel21.setText("Semestre:");

        jLabel22.setText("Dirección:");

        FNombrePrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FNombreProgramaActionPerformed(evt);
            }
        });

        comboDirecciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboDireccionesMouseClicked(evt);
            }
        });

        AgregarPrograma.setText("Agregar");
        AgregarPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProgramaActionPerformed(evt);
            }
        });

        ModificarPrograma.setText("Modificar");
        ModificarPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarProgramaActionPerformed(evt);
            }
        });

        EliminarPrograma.setText("Eliminar");
        EliminarPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarProgramaActionPerformed(evt);
            }
        });

        NuevoPrograma.setText("Nuevo");
        NuevoPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoProgramaActionPerformed(evt);
            }
        });

        tablaProgramas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Semestre", "Direccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaProgramas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProgramasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaProgramas);
        if (tablaProgramas.getColumnModel().getColumnCount() > 0) {
            tablaProgramas.getColumnModel().getColumn(0).setMinWidth(50);
            tablaProgramas.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaProgramas.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout panelProgramasLayout = new javax.swing.GroupLayout(panelProgramas);
        panelProgramas.setLayout(panelProgramasLayout);
        panelProgramasLayout.setHorizontalGroup(
            panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProgramasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addGap(36, 36, 36)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FIdPrograma)
                    .addComponent(FNombrePrograma)
                    .addComponent(FSemestre)
                    .addComponent(comboDirecciones, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProgramasLayout.createSequentialGroup()
                .addGap(0, 66, Short.MAX_VALUE)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProgramasLayout.createSequentialGroup()
                        .addComponent(AgregarPrograma)
                        .addGap(62, 62, 62)
                        .addComponent(ModificarPrograma)
                        .addGap(49, 49, 49)
                        .addComponent(EliminarPrograma)
                        .addGap(43, 43, 43)
                        .addComponent(NuevoPrograma))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProgramasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(242, 242, 242))
        );
        panelProgramasLayout.setVerticalGroup(
            panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProgramasLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel10)
                .addGap(26, 26, 26)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(FIdPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(FNombrePrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(FSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(comboDirecciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarPrograma)
                    .addComponent(ModificarPrograma)
                    .addComponent(EliminarPrograma)
                    .addComponent(NuevoPrograma))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        panel.addTab("tab4", panelProgramas);

        panelEstudiantes.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Estudiantes");

        jLabel23.setText("Id:");

        jLabel24.setText("Nombre:");

        jLabel25.setText("Apellido:");

        jLabel26.setText("Código:");

        jLabel27.setText("Programa:");

        jLabel28.setText("Dirección:");

        comboProgramas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboProgramasMouseClicked(evt);
            }
        });

        comboDirecciones2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboDirecciones2MouseClicked(evt);
            }
        });

        AgregarEstudiante.setText("Agregar");
        AgregarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarEstudianteActionPerformed(evt);
            }
        });

        ModificarEstudiante.setText("Modificar");
        ModificarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarEstudianteActionPerformed(evt);
            }
        });

        EliminarEstudiante.setText("Eliminar");
        EliminarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarEstudianteActionPerformed(evt);
            }
        });

        NuevoEstudiante.setText("Nuevo");
        NuevoEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoEstudianteActionPerformed(evt);
            }
        });

        tablaEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido", "Codigo", "Programa", "Direccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEstudiantesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaEstudiantes);
        if (tablaEstudiantes.getColumnModel().getColumnCount() > 0) {
            tablaEstudiantes.getColumnModel().getColumn(0).setMinWidth(50);
            tablaEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaEstudiantes.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout panelEstudiantesLayout = new javax.swing.GroupLayout(panelEstudiantes);
        panelEstudiantes.setLayout(panelEstudiantesLayout);
        panelEstudiantesLayout.setHorizontalGroup(
            panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstudiantesLayout.createSequentialGroup()
                .addGap(0, 50, Short.MAX_VALUE)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelEstudiantesLayout.createSequentialGroup()
                        .addComponent(AgregarEstudiante)
                        .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEstudiantesLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel27)
                                    .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel25))
                                        .addGroup(panelEstudiantesLayout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jLabel26)))
                                    .addComponent(jLabel28))
                                .addGap(27, 27, 27)
                                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addComponent(FIdEstudiante)
                                    .addComponent(FNombreEstudiante)
                                    .addComponent(FApellidoEstudiante)
                                    .addComponent(FCodigo)
                                    .addComponent(comboProgramas, 0, 114, Short.MAX_VALUE)
                                    .addComponent(comboDirecciones2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelEstudiantesLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(ModificarEstudiante)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EliminarEstudiante)
                                .addGap(50, 50, 50)))
                        .addComponent(NuevoEstudiante))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        panelEstudiantesLayout.setVerticalGroup(
            panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstudiantesLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(FIdEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(FNombreEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(FApellidoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(FCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(comboProgramas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(comboDirecciones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ModificarEstudiante)
                        .addComponent(AgregarEstudiante))
                    .addGroup(panelEstudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EliminarEstudiante)
                        .addComponent(NuevoEstudiante)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        panel.addTab("tab5", panelEstudiantes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDepartamentosMouseClicked
        panel.setSelectedIndex(0);
        limpiarTablaD();
        listarDepartamentos(departamentoRepository.read());
    }//GEN-LAST:event_btnDepartamentosMouseClicked

    private void btnMunicipiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMunicipiosMouseClicked
        panel.setSelectedIndex(1);
        limpiarTablaM();
        listarMunicipios(municipioRepository.read());
        comboBoxDepartamentos(departamentoRepository.read(), comboDepartamentos); 
    }//GEN-LAST:event_btnMunicipiosMouseClicked

    private void btnLugaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLugaresMouseClicked
        panel.setSelectedIndex(2);
        limpiarTablaL();
        listarLugares(lugarRepository.read());
        comboBoxDepartamentos(departamentoRepository.read(), comboDepartamentos2); 
        comboBoxMunicipios(municipioRepository.read(), comboMunicipios); 
    }//GEN-LAST:event_btnLugaresMouseClicked

    private void btnProgramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProgramasMouseClicked
        panel.setSelectedIndex(3);
        limpiarTablaP();
        listarProgramas(programaRepository.read());
        comboBoxLugares(lugarRepository.read(), comboDirecciones); 
    }//GEN-LAST:event_btnProgramasMouseClicked

    private void btnEstudiantesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstudiantesMouseClicked
        panel.setSelectedIndex(4);
        limpiarTablaE();
        listarEstudiantes(estudianteRepository.read());
        comboBoxProgramas(programaRepository.read(), comboProgramas);
        comboBoxLugares(lugarRepository.read(), comboDirecciones2);
    }//GEN-LAST:event_btnEstudiantesMouseClicked

    private void FIdDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FIdDepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FIdDepartamentoActionPerformed

    private void AgregarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarDepartamentoActionPerformed
       agregarDepartamento();
       listarDepartamentos(departamentoRepository.read());
       nuevoDepartamento();
    }//GEN-LAST:event_AgregarDepartamentoActionPerformed

    private void ModificarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarDepartamentoActionPerformed
        modificarDepartamento();
        listarDepartamentos(departamentoRepository.read());
        nuevoDepartamento();
    }//GEN-LAST:event_ModificarDepartamentoActionPerformed

    private void EliminarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarDepartamentoActionPerformed
        try {
            eliminarDepartamento();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarDepartamentos(departamentoRepository.read());
        nuevoDepartamento();
    }//GEN-LAST:event_EliminarDepartamentoActionPerformed

    private void NuevoDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoDepartamentoActionPerformed
       nuevoDepartamento();
    }//GEN-LAST:event_NuevoDepartamentoActionPerformed

    private void FIdMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FIdMunicipioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FIdMunicipioActionPerformed

    private void FNombreMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FNombreMunicipioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FNombreMunicipioActionPerformed

    private void AgregarMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarMunicipioActionPerformed
        agregarMunicipio();
        listarMunicipios(municipioRepository.read());
        nuevoMunicipio();
    }//GEN-LAST:event_AgregarMunicipioActionPerformed

    private void tablaMunicipiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMunicipiosMouseClicked
        int fila = tablaMunicipios.getSelectedRow();
       if(fila == -1){
           JOptionPane.showMessageDialog(null, "Usuario no seleccionado");
       }else{
           idMunicipio = Integer.parseInt((String)tablaMunicipios.getValueAt(fila, 0).toString());
           String nombreMunicipio = (String)tablaMunicipios.getValueAt(fila, 1);
           String departamento = (String)tablaMunicipios.getValueAt(fila, 2);
           FIdMunicipio.setText(""+idMunicipio);
           FNombreMunicipio.setText(""+nombreMunicipio);
           comboDepartamentos.setSelectedItem(departamento);
           FIdMunicipio.setEditable(false);
           FIdMunicipio.setEnabled(false);
       }
    }//GEN-LAST:event_tablaMunicipiosMouseClicked

    private void ModificarMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarMunicipioActionPerformed
       modificarMunicipio();
       listarMunicipios(municipioRepository.read());
       nuevoMunicipio();
    }//GEN-LAST:event_ModificarMunicipioActionPerformed

    private void EliminarMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarMunicipioActionPerformed
        try {
            eliminarMunicipio();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarMunicipios(municipioRepository.read());
        nuevoMunicipio();
    }//GEN-LAST:event_EliminarMunicipioActionPerformed

    private void NuevoMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoMunicipioActionPerformed
       nuevoMunicipio();
    }//GEN-LAST:event_NuevoMunicipioActionPerformed

    private void comboDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboDepartamentosMouseClicked
      //comboBoxDepartamentos(departamentoRepository.read(), comboDepartamentos); 
    }//GEN-LAST:event_comboDepartamentosMouseClicked

    private void FDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FDireccionActionPerformed

    private void tablaLugaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaLugaresMouseClicked
        int fila = tablaLugares.getSelectedRow();
       if(fila == -1){
           JOptionPane.showMessageDialog(null, "Usuario no seleccionado");
       }else{
           direccion = (String)tablaLugares.getValueAt(fila, 0);
           String departamento = (String)tablaLugares.getValueAt(fila, 1);
           String municipio = (String)tablaLugares.getValueAt(fila, 2);
           FDireccion.setText(""+direccion);
           comboDepartamentos2.setSelectedItem(departamento);
           comboMunicipios.setSelectedItem(municipio);
           FDireccion.setEditable(false);
           FDireccion.setEnabled(false);
       }
    }//GEN-LAST:event_tablaLugaresMouseClicked

    private void AgregarLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarLugarActionPerformed
        agregarLugar();
        listarLugares(lugarRepository.read());
        nuevoLugar();
        
    }//GEN-LAST:event_AgregarLugarActionPerformed

    private void ModificarLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarLugarActionPerformed
       modificarLugar();
       listarLugares(lugarRepository.read());
       nuevoLugar();
    }//GEN-LAST:event_ModificarLugarActionPerformed

    private void EliminarLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarLugarActionPerformed
       try {
            eliminarLugar();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarLugares(lugarRepository.read());
        nuevoLugar();
    }//GEN-LAST:event_EliminarLugarActionPerformed

    private void NuevoLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoLugarActionPerformed
        nuevoLugar();
    }//GEN-LAST:event_NuevoLugarActionPerformed

    private void comboDepartamentos2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboDepartamentos2MouseClicked
       comboBoxDepartamentos(departamentoRepository.read(), comboDepartamentos2); 
    }//GEN-LAST:event_comboDepartamentos2MouseClicked

    private void comboMunicipiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboMunicipiosMouseClicked
       comboBoxMunicipios(municipioRepository.read(), comboMunicipios); 
    }//GEN-LAST:event_comboMunicipiosMouseClicked

    private void FNombreProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FNombreProgramaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FNombreProgramaActionPerformed

    private void tablaProgramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProgramasMouseClicked
        int fila = tablaProgramas.getSelectedRow();
       if(fila == -1){
           JOptionPane.showMessageDialog(null, "Usuario no seleccionado");
       }else{
           idPrograma = Integer.parseInt((String)tablaProgramas.getValueAt(fila, 0).toString());
           String nombrePrograma = (String)tablaProgramas.getValueAt(fila, 1);
           int semestre = Integer.parseInt((String)tablaProgramas.getValueAt(fila, 2).toString());
           String direccion = (String)tablaProgramas.getValueAt(fila, 3);
           FIdPrograma.setText(""+idPrograma);
           FNombrePrograma.setText(""+nombrePrograma);
           FSemestre.setText(""+semestre);
           comboDirecciones.setSelectedItem(direccion);
           FIdPrograma.setEditable(false);
           FIdPrograma.setEnabled(false);
       }
    }//GEN-LAST:event_tablaProgramasMouseClicked

    private void AgregarProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProgramaActionPerformed
       agregarPrograma();
       listarProgramas(programaRepository.read());
       nuevoPrograma();
    }//GEN-LAST:event_AgregarProgramaActionPerformed

    private void ModificarProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarProgramaActionPerformed
       modificarPrograma();
       listarProgramas(programaRepository.read());
       nuevoPrograma();
    }//GEN-LAST:event_ModificarProgramaActionPerformed

    private void EliminarProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarProgramaActionPerformed
       try {
            eliminarPrograma();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarProgramas(programaRepository.read());
        nuevoPrograma();
    }//GEN-LAST:event_EliminarProgramaActionPerformed

    private void NuevoProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoProgramaActionPerformed
       nuevoPrograma();
    }//GEN-LAST:event_NuevoProgramaActionPerformed

    private void comboDireccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboDireccionesMouseClicked
        comboBoxLugares(lugarRepository.read(), comboDirecciones); 
    }//GEN-LAST:event_comboDireccionesMouseClicked

    private void tablaDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDepartamentosMouseClicked
        int fila = tablaDepartamentos.getSelectedRow();
       if(fila == -1){
           JOptionPane.showMessageDialog(null, "Usuario no seleccionado");
       }else{
           idDepartamento = Integer.parseInt((String)tablaDepartamentos.getValueAt(fila, 0).toString());
           String nombreDepartamento = (String)tablaDepartamentos.getValueAt(fila, 1);
           FIdDepartamento.setText(""+idDepartamento);
           FNombreDepartamento.setText(""+nombreDepartamento);
           FIdDepartamento.setEditable(false);
           FIdDepartamento.setEnabled(false);
       }
    }//GEN-LAST:event_tablaDepartamentosMouseClicked

    private void ModificarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarEstudianteActionPerformed
       modificarEstudiante();
       listarEstudiantes(estudianteRepository.read());
       nuevoEstudiante();
    }//GEN-LAST:event_ModificarEstudianteActionPerformed

    private void tablaEstudiantesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEstudiantesMouseClicked
        int fila = tablaEstudiantes.getSelectedRow();
       if(fila == -1){
           JOptionPane.showMessageDialog(null, "Usuario no seleccionado");
       }else{
           idEstudiante = Integer.parseInt((String)tablaEstudiantes.getValueAt(fila, 0).toString());
           String nombre = (String)tablaEstudiantes.getValueAt(fila, 1);
           String apellido = (String)tablaEstudiantes.getValueAt(fila, 2);
           double codigo = Double.parseDouble((String)tablaEstudiantes.getValueAt(fila, 3).toString());
           String programa = (String)tablaEstudiantes.getValueAt(fila, 4);
           String direccion = (String)tablaEstudiantes.getValueAt(fila, 5);
           
           FIdEstudiante.setText(""+idEstudiante);
           FNombreEstudiante.setText(""+nombre);
           FApellidoEstudiante.setText(""+apellido);
           FCodigo.setText(""+codigo);
           comboProgramas.setSelectedItem(programa);
           comboDirecciones2.setSelectedItem(direccion);
           FIdEstudiante.setEditable(false);
           FIdEstudiante.setEnabled(false);
       }
    }//GEN-LAST:event_tablaEstudiantesMouseClicked

    private void AgregarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarEstudianteActionPerformed
       agregarEstudiante();
       listarEstudiantes(estudianteRepository.read());
       nuevoEstudiante();
    }//GEN-LAST:event_AgregarEstudianteActionPerformed

    private void EliminarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarEstudianteActionPerformed
       try {
            eliminarEstudiante();
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarEstudiantes(estudianteRepository.read());
        nuevoEstudiante();
        
    }//GEN-LAST:event_EliminarEstudianteActionPerformed

    private void NuevoEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoEstudianteActionPerformed
        nuevoEstudiante();
    }//GEN-LAST:event_NuevoEstudianteActionPerformed

    private void comboProgramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboProgramasMouseClicked
        comboBoxProgramas(programaRepository.read(), comboProgramas);
    }//GEN-LAST:event_comboProgramasMouseClicked

    private void comboDirecciones2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboDirecciones2MouseClicked
         comboBoxLugares(lugarRepository.read(), comboDirecciones2);
    }//GEN-LAST:event_comboDirecciones2MouseClicked

    private void btnDepartamentosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDepartamentosMouseEntered
        btnDepartamentos.setBackground(new Color(255,255,255));
        txtdep.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnDepartamentosMouseEntered

    private void btnDepartamentosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDepartamentosMouseExited
       btnDepartamentos.setBackground(new Color(51,102,255));
       txtdep.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnDepartamentosMouseExited

    private void btnMunicipiosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMunicipiosMouseEntered
        btnMunicipios.setBackground(new Color(255,255,255));
        txtmun.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnMunicipiosMouseEntered

    private void btnMunicipiosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMunicipiosMouseExited
        btnMunicipios.setBackground(new Color(51,102,255));
       txtmun.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnMunicipiosMouseExited

    private void btnLugaresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLugaresMouseEntered
        btnLugares.setBackground(new Color(255,255,255));
        txtdir.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnLugaresMouseEntered

    private void btnLugaresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLugaresMouseExited
        btnLugares.setBackground(new Color(51,102,255));
       txtdir.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnLugaresMouseExited

    private void btnProgramasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProgramasMouseEntered
        btnProgramas.setBackground(new Color(255,255,255));
        txtpro.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnProgramasMouseEntered

    private void btnProgramasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProgramasMouseExited
         btnProgramas.setBackground(new Color(51,102,255));
       txtpro.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnProgramasMouseExited

    private void btnEstudiantesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstudiantesMouseEntered
        btnEstudiantes.setBackground(new Color(255,255,255));
        txtest.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnEstudiantesMouseEntered

    private void btnEstudiantesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstudiantesMouseExited
         btnEstudiantes.setBackground(new Color(51,102,255));
       txtest.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnEstudiantesMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarDepartamento;
    private javax.swing.JButton AgregarEstudiante;
    private javax.swing.JButton AgregarLugar;
    private javax.swing.JButton AgregarMunicipio;
    private javax.swing.JButton AgregarPrograma;
    private javax.swing.JButton EliminarDepartamento;
    private javax.swing.JButton EliminarEstudiante;
    private javax.swing.JButton EliminarLugar;
    private javax.swing.JButton EliminarMunicipio;
    private javax.swing.JButton EliminarPrograma;
    private javax.swing.JTextField FApellidoEstudiante;
    private javax.swing.JTextField FCodigo;
    private javax.swing.JTextField FDireccion;
    private javax.swing.JTextField FIdDepartamento;
    private javax.swing.JTextField FIdEstudiante;
    private javax.swing.JTextField FIdMunicipio;
    private javax.swing.JTextField FIdPrograma;
    private javax.swing.JTextField FNombreDepartamento;
    private javax.swing.JTextField FNombreEstudiante;
    private javax.swing.JTextField FNombreMunicipio;
    private javax.swing.JTextField FNombrePrograma;
    private javax.swing.JTextField FSemestre;
    private javax.swing.JButton ModificarDepartamento;
    private javax.swing.JButton ModificarEstudiante;
    private javax.swing.JButton ModificarLugar;
    private javax.swing.JButton ModificarMunicipio;
    private javax.swing.JButton ModificarPrograma;
    private javax.swing.JButton NuevoDepartamento;
    private javax.swing.JButton NuevoEstudiante;
    private javax.swing.JButton NuevoLugar;
    private javax.swing.JButton NuevoMunicipio;
    private javax.swing.JButton NuevoPrograma;
    private javax.swing.JPanel btnDepartamentos;
    private javax.swing.JPanel btnEstudiantes;
    private javax.swing.JPanel btnLugares;
    private javax.swing.JPanel btnMunicipios;
    private javax.swing.JPanel btnProgramas;
    private javax.swing.JComboBox<String> comboDepartamentos;
    private javax.swing.JComboBox<String> comboDepartamentos2;
    private javax.swing.JComboBox<String> comboDirecciones;
    private javax.swing.JComboBox<String> comboDirecciones2;
    private javax.swing.JComboBox<String> comboMunicipios;
    private javax.swing.JComboBox<String> comboProgramas;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane panel;
    private javax.swing.JPanel panelDepartamentos;
    private javax.swing.JPanel panelEstudiantes;
    private javax.swing.JPanel panelLugares;
    private javax.swing.JPanel panelMunicipios;
    private javax.swing.JPanel panelProgramas;
    private javax.swing.JTable tablaDepartamentos;
    private javax.swing.JTable tablaEstudiantes;
    private javax.swing.JTable tablaLugares;
    private javax.swing.JTable tablaMunicipios;
    private javax.swing.JTable tablaProgramas;
    private javax.swing.JLabel txtdep;
    private javax.swing.JLabel txtdir;
    private javax.swing.JLabel txtest;
    private javax.swing.JLabel txtmun;
    private javax.swing.JLabel txtpro;
    // End of variables declaration//GEN-END:variables

    
}
