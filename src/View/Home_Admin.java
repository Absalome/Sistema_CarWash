package View;

import connect.Conexao;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.PatternSyntaxException;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;


public class Home_Admin extends javax.swing.JFrame {

    private Component jPainel2;

    

    /**
     * Creates new form Funcionários
     */
    public Home_Admin() {
        initComponents();
        P11.setVisible(false);  
         carregarFuncionariosNaTabela(); 
         carregarAgendamentosFeitos ();
         carregarClientesNaTabela();
         

         
    }
private void carregarFuncionariosNaTabela() {
    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "SELECT * FROM funcionarios";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTableFuncionarios.getModel();
        model.setRowCount(0);  

        while (rs.next()) {
            Object[] row = {
                rs.getString("nome_completo"),
                rs.getString("sexo"),
                rs.getString("email"),
                rs.getString("cargo"),
                rs.getString("morada"),
                rs.getString("contacto"),
                rs.getString("salario"),
                rs.getString("hora_entrada"),
                rs.getString("hora_saida")
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + e.getMessage());
    }
}

private javax.swing.JTextField jTextFieldId = new javax.swing.JTextField();


// Preços dos serviços
private final Map<String, Integer> precosServicos = Map.of(
    "Lavagem Interna", 1000,
    "Lavagem Externa", 800,
    "Polimento e Enceramento", 10000,
    "Lavagem Completa", 1700,
    "Lavagem de Rodas e Pneus", 500,
    "Hibernização e tratamento de bancos", 1500
);

private void calcularTotal() {
    double total = 0.0;

    // Verificando o tipo de viatura no JComboBox e adicionando o preço correspondente
    String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();
    switch (tipoViatura) {
        case "Pesado":
            total += 1100;
            break;
        case "Especial":
            total += 700;
            break;
        case "Ligeiro Pessoal":
            total += 500;
            break;
        case "Motocicleta":
            total += 250;
            break;
        default:
            JOptionPane.showMessageDialog(this, "Tipo de viatura desconhecido.");
            return;
    }
    
    jComboBoxTipoViatura.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        calcularTotal();
    }
});
    
    
    String tipoMotor = jComboBoxTipoMotor.getSelectedItem().toString();
    switch (tipoMotor) {
        case "VVTI":
            total += 250;
            break;
        case "V2":
        case "V3":
            total += 300;
            break;
        case "V6":
            total += 400;
            break;
        case "V8":
            total += 600;
            break;
    }
    
    
jComboBoxTipoMotor.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        calcularTotal(); 
    }
});



    // Atualizando o campo de total com o resultado final
    jTextFieldTotal.setText(String.valueOf(total));
}


private void carregarAgendamentos() {
    DefaultTableModel model = (DefaultTableModel) jTableAgendamentos.getModel();
    model.setRowCount(0); 

    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "SELECT * FROM agendamentos"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            // Adicionando os dados à tabela
            model.addRow(new Object[]{
                rs.getInt("id"), // ID
                rs.getString("marca"),
                rs.getString("placa"),
                rs.getString("proprietario"),
                rs.getString("contacto"),
                rs.getString("tipo_viatura"),
                rs.getString("hora_entrada"),
                rs.getString("hora_saida"),
                rs.getDouble("total_servicos")
            });
        }
        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos: " + e.getMessage());
    }
}




private void carregarClientesNaTabela() {
    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "SELECT * FROM clientes";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTableClientes.getModel();
        model.setRowCount(0);  

        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("sexo"),
                rs.getString("email"),
                rs.getString("contacto"),
                rs.getString("morada"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("matricula"),
                rs.getString("status")
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar Cliente: " + e.getMessage());
    }
}



private void jTableAgendamentosMouseClicked(java.awt.event.MouseEvent evt) {
    int selectedRow = jTableAgendamentos.getSelectedRow();
    if (selectedRow != -1) {
       
        String id = jTableAgendamentos.getValueAt(selectedRow, 1).toString(); 
        String marca = jTableAgendamentos.getValueAt(selectedRow, 2).toString(); 
        String placa = jTableAgendamentos.getValueAt(selectedRow, 3).toString(); 
        String proprietario = jTableAgendamentos.getValueAt(selectedRow, 4).toString(); 
        String contacto = jTableAgendamentos.getValueAt(selectedRow, 5).toString(); 
        String tipoViatura = jTableAgendamentos.getValueAt(selectedRow, 6).toString(); 
        String horaEntrada = jTableAgendamentos.getValueAt(selectedRow, 7).toString(); 
        String horaSaida = jTableAgendamentos.getValueAt(selectedRow, 8).toString(); 
        double total = Double.parseDouble(jTableAgendamentos.getValueAt(selectedRow, 9).toString()); 

        // Preenchendo os campos com os dados corretos
        jTextFieldId.setText(id);
        jTextFieldMarca.setText(marca);
        jTextFieldPlaca.setText(placa);
        jTextFieldProprietario.setText(proprietario);
        jTextFieldContacto2.setText(contacto);
        jComboBoxTipoViatura.setSelectedItem(tipoViatura);
        jTextFieldHoraEntrada.setText(horaEntrada); 
        jTextFieldHoraSaida.setText(horaSaida); 
        jTextFieldTotal.setText(String.valueOf(total));
    }
}

private void carregarAgendamentosFeitos() {
    DefaultTableModel modelFeitos = (DefaultTableModel) jTableFeitos.getModel();
    
    try {
        java.sql.Connection con = Conexao.connect(); // Conecta ao banco de dados
        
        String sql = "SELECT * FROM agendamentos_feitos";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Object[] rowData = new Object[9]; // 9 colunas na JTable
            rowData[0] = rs.getInt("id");
            rowData[1] = rs.getString("marca");
            rowData[2] = rs.getString("placa");
            rowData[3] = rs.getString("proprietario");
            rowData[4] = rs.getString("contacto");
            rowData[5] = rs.getString("tipo_viatura");
            rowData[6] = rs.getTime("hora_entrada");
            rowData[7] = rs.getTime("hora_saida");
            rowData[8] = rs.getBigDecimal("total_servicos");

            modelFeitos.addRow(rowData); // Adiciona à JTableFeitos
        }
        
        rs.close();
        pst.close();
        con.close();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos feitos: " + e.getMessage());
    }
}





    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        Paineis = new javax.swing.JTabbedPane();
        P3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        P5 = new javax.swing.JPanel();
        P6 = new javax.swing.JPanel();
        P7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldMarca = new javax.swing.JTextField();
        jTextFieldPlaca = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButtonSalvarAgendamento = new javax.swing.JButton();
        jTextFieldTotal = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldProprietario = new javax.swing.JTextField();
        jComboBoxTipoViatura = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldHoraEntrada = new javax.swing.JTextField();
        jTextFieldHoraSaida = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldContacto2 = new javax.swing.JTextField();
        jComboBoxTipoMotor = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        LavagemInterna = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        LavagemExterna = new javax.swing.JTextField();
        LavagemCompleta = new javax.swing.JTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel34 = new javax.swing.JLabel();
        PolimentoEnceramento = new javax.swing.JTextField();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel36 = new javax.swing.JLabel();
        LavagemRodasPneus = new javax.swing.JTextField();
        jCheckBox6 = new javax.swing.JCheckBox();
        P1 = new javax.swing.JPanel();
        P13 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        sexo = new javax.swing.JLabel();
        nomeField = new javax.swing.JTextField();
        email = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        contacto = new javax.swing.JLabel();
        moradaField = new javax.swing.JTextField();
        Cadastrar = new javax.swing.JButton();
        P14 = new javax.swing.JPanel();
        marca = new javax.swing.JLabel();
        modelo = new javax.swing.JLabel();
        matricula = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        marcaField = new javax.swing.JTextField();
        modeloField = new javax.swing.JTextField();
        jTextFieldMatricula = new javax.swing.JTextField();
        morada = new javax.swing.JLabel();
        contactoField = new javax.swing.JTextField();
        nome = new javax.swing.JLabel();
        sexoComboBox = new javax.swing.JComboBox<>();
        P2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNomeCompleto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldContacto = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonSalvarEdicao = new javax.swing.JButton();
        P11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxHoraEntrada = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxHoraSaida = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldSalario = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFuncionarios = new javax.swing.JTable();
        jComboBoxCargo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldMorada = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxSexo = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldSenha = new javax.swing.JTextField();
        P8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAgendamentos = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButtonSalvarEdicao2 = new javax.swing.JButton();
        jButtonRemover = new javax.swing.JButton();
        jButtonFeito = new javax.swing.JButton();
        P9 = new javax.swing.JPanel();
        P12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableFeitos = new javax.swing.JTable();
        P4 = new javax.swing.JPanel();
        P10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jButtonEditar1 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        pesquisajTextField = new javax.swing.JTextField();
        P16 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jTextFieldLavagemInterna = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jTextFieldLavagemCompleta = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextFieldLavagemRodasPneus = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel86 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jLabel93 = new javax.swing.JLabel();
        jTextFieldLavagemExterna = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jTextFieldPolimentoEnceramento = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jTextFieldLavagemExterna2 = new javax.swing.JTextField();
        jTextFieldLavagemInterna2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldLavagemCompleta2 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jTextFieldPolimentoEnceramento2 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jTextFieldLavagemRodasPneus2 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jTextFieldLavagemInterna3 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jTextFieldLavagemExterna3 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jTextFieldLavagemCompleta3 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jTextFieldPolimentoEnceramento3 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jTextFieldLavagemRodasPneus3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 750));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/ABSA_Car_wash-removebg-preview (1).png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 153));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-add-administrator-32.png"))); // NOI18N
        jButton1.setText("Cadastrar Funcionário");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 220, -1));

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/home-40.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setMargin(new java.awt.Insets(2, 20, 3, 2));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 177, 188, 39));

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-eye-32.png"))); // NOI18N
        jButton3.setText("    Ver  Dados Clientes");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 220, 37));

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-reports-32.png"))); // NOI18N
        jButton4.setText("      Relatório");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 220, 35));

        jButton7.setBackground(new java.awt.Color(204, 204, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 0, 0));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-services-32.png"))); // NOI18N
        jButton7.setText(" Agend. Pendentes");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 220, 38));

        jButton8.setBackground(new java.awt.Color(204, 204, 204));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 0, 0));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-agenda-32.png"))); // NOI18N
        jButton8.setText("Agendamentos");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 220, 38));

        jButton9.setBackground(new java.awt.Color(204, 204, 204));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 0, 0));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-warehouse-32.png"))); // NOI18N
        jButton9.setText("Gestão de Estoque");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 220, 36));

        jButton11.setBackground(new java.awt.Color(204, 204, 204));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(0, 0, 0));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-eye-32.png"))); // NOI18N
        jButton11.setText("Ver  Dados dos Funcionários");
        jButton11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 220, 37));

        jButton6.setText("Agendamentos (Feito)");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 220, 40));

        jButton14.setBackground(new java.awt.Color(204, 204, 204));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton14.setForeground(new java.awt.Color(0, 0, 0));
        jButton14.setText("Cadastrar Cliente");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 220, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 700));

        P3.setBackground(new java.awt.Color(0, 204, 204));
        P3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("BEM VINDO GERENTE");
        P3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 280, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/pngwing.com 2.png"))); // NOI18N
        P3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -10, 940, 690));

        Paineis.addTab("tab1", P3);

        P5.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout P5Layout = new javax.swing.GroupLayout(P5);
        P5.setLayout(P5Layout);
        P5Layout.setHorizontalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1670, Short.MAX_VALUE)
        );
        P5Layout.setVerticalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
        );

        Paineis.addTab("tab2", P5);

        P6.setBackground(new java.awt.Color(51, 0, 51));
        P6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                P6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout P6Layout = new javax.swing.GroupLayout(P6);
        P6.setLayout(P6Layout);
        P6Layout.setHorizontalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1670, Short.MAX_VALUE)
        );
        P6Layout.setVerticalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
        );

        Paineis.addTab("tab3", P6);

        P7.setBackground(new java.awt.Color(0, 204, 153));
        P7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Marca");
        P7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Placa");
        P7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, 28));

        jTextFieldMarca.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 138, -1));

        jTextFieldPlaca.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 138, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("AGENDAMENTOS");
        P7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 28, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Valor A pagar");
        P7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 560, -1, -1));

        jButtonSalvarAgendamento.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAgendamento.setForeground(new java.awt.Color(0, 0, 0));
        jButtonSalvarAgendamento.setText("Salvar");
        jButtonSalvarAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAgendamentoActionPerformed(evt);
            }
        });
        P7.add(jButtonSalvarAgendamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 510, -1, -1));

        jTextFieldTotal.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTotal.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        P7.add(jTextFieldTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 610, 100, 25));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Contacto");
        P7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Proprietário");
        P7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, -1, -1));

        jTextFieldProprietario.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldProprietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 120, -1));

        jComboBoxTipoViatura.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxTipoViatura.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxTipoViatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ligeiro", "Medio", "Pesado" }));
        jComboBoxTipoViatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoViaturaActionPerformed(evt);
            }
        });
        P7.add(jComboBoxTipoViatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 130, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Hora de Entrada");
        P7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 96, -1, 20));

        jTextFieldHoraEntrada.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldHoraEntrada.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldHoraEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 120, -1));

        jTextFieldHoraSaida.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldHoraSaida.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldHoraSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 140, 120, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Hora de Saida");
        P7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, -1, -1));

        jTextFieldContacto2.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldContacto2.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldContacto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 120, -1));

        jComboBoxTipoMotor.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxTipoMotor.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxTipoMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VVTI", "V2 e V3", "V6", "V8" }));
        P7.add(jComboBoxTipoMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 280, 110, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Categoria");
        P7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 60, 20));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Tipo de Motor");
        P7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 280, -1, -1));

        jButton18.setText("jButton18");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        P7.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Lavagem Completa");
        P7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, -1, -1));

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Lavagem Interna");
        P7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, -1, -1));

        LavagemInterna.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(LavagemInterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, -1, -1));
        P7.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, -1, -1));
        P7.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, -1, -1));

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Lavagem Externa");
        P7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, -1, -1));

        LavagemExterna.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(LavagemExterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, -1, -1));

        LavagemCompleta.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(LavagemCompleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, -1, -1));
        P7.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, -1, -1));

        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Polimento e Enceramento");
        P7.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, -1, -1));

        PolimentoEnceramento.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(PolimentoEnceramento, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 340, -1, -1));
        P7.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 340, -1, -1));

        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Hibernização e Tratamento de Bancos");
        P7.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, 210, -1));

        jTextField6.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, -1, -1));
        P7.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, -1, -1));

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Lavagem de Rodas e Pneus");
        P7.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 420, -1, -1));

        LavagemRodasPneus.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(LavagemRodasPneus, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 410, -1, -1));
        P7.add(jCheckBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, -1, -1));

        Paineis.addTab("tab4", P7);

        P1.setBackground(new java.awt.Color(102, 255, 102));

        P13.setBackground(new java.awt.Color(0, 204, 204));
        P13.setForeground(new java.awt.Color(0, 0, 0));
        P13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("CADASTRO DE CLIENTES");
        P13.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 6, -1, 38));

        sexo.setBackground(new java.awt.Color(0, 0, 0));
        sexo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sexo.setForeground(new java.awt.Color(0, 0, 0));
        sexo.setText("Sexo");
        sexo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P13.add(sexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, -1, -1));

        nomeField.setBackground(new java.awt.Color(255, 255, 255));
        nomeField.setForeground(new java.awt.Color(0, 0, 0));
        nomeField.setBorder(null);
        nomeField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        P13.add(nomeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 170, 24));

        email.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        email.setForeground(new java.awt.Color(0, 0, 0));
        email.setText("Email");
        P13.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        emailField.setBackground(new java.awt.Color(255, 255, 255));
        emailField.setForeground(new java.awt.Color(0, 0, 0));
        emailField.setBorder(null);
        emailField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailFieldKeyTyped(evt);
            }
        });
        P13.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 170, 22));

        contacto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        contacto.setForeground(new java.awt.Color(0, 0, 0));
        contacto.setText("Contacto");
        P13.add(contacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        moradaField.setBackground(new java.awt.Color(255, 255, 255));
        moradaField.setForeground(new java.awt.Color(0, 0, 0));
        moradaField.setBorder(null);
        P13.add(moradaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 170, 22));

        Cadastrar.setBackground(new java.awt.Color(51, 255, 51));
        Cadastrar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Cadastrar.setForeground(new java.awt.Color(0, 0, 0));
        Cadastrar.setText("Cadastrar");
        Cadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastrarActionPerformed(evt);
            }
        });
        P13.add(Cadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, -1, -1));

        P14.setBackground(new java.awt.Color(0, 153, 153));

        marca.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        marca.setForeground(new java.awt.Color(0, 0, 0));
        marca.setText("Marca");

        modelo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        modelo.setForeground(new java.awt.Color(0, 0, 0));
        modelo.setText("Modelo");

        matricula.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        matricula.setForeground(new java.awt.Color(0, 0, 0));
        matricula.setText("Matricula");

        statusComboBox.setBackground(new java.awt.Color(255, 255, 255));
        statusComboBox.setForeground(new java.awt.Color(0, 0, 0));
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Status");

        marcaField.setBackground(new java.awt.Color(255, 255, 255));
        marcaField.setForeground(new java.awt.Color(0, 0, 0));

        modeloField.setBackground(new java.awt.Color(255, 255, 255));
        modeloField.setForeground(new java.awt.Color(0, 0, 0));

        jTextFieldMatricula.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMatricula.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMatriculaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout P14Layout = new javax.swing.GroupLayout(P14);
        P14.setLayout(P14Layout);
        P14Layout.setHorizontalGroup(
            P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P14Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marca)
                    .addComponent(matricula)
                    .addComponent(jLabel30))
                .addGap(37, 37, 37)
                .addGroup(P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(marcaField)
                    .addComponent(statusComboBox, 0, 171, Short.MAX_VALUE)
                    .addComponent(modeloField)
                    .addComponent(jTextFieldMatricula))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        P14Layout.setVerticalGroup(
            P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P14Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marca)
                    .addComponent(marcaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modelo)
                    .addComponent(modeloField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matricula)
                    .addComponent(jTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        P13.add(P14, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 390, 290));

        morada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        morada.setForeground(new java.awt.Color(0, 0, 0));
        morada.setText("Morada");
        P13.add(morada, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, -1, -1));

        contactoField.setBackground(new java.awt.Color(255, 255, 255));
        contactoField.setForeground(new java.awt.Color(0, 0, 0));
        contactoField.setBorder(null);
        contactoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactoFieldActionPerformed(evt);
            }
        });
        contactoField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contactoFieldKeyTyped(evt);
            }
        });
        P13.add(contactoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 170, 20));

        nome.setBackground(new java.awt.Color(0, 0, 0));
        nome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nome.setForeground(new java.awt.Color(0, 0, 0));
        nome.setText("Nome");
        P13.add(nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        sexoComboBox.setBackground(new java.awt.Color(255, 255, 255));
        sexoComboBox.setForeground(new java.awt.Color(0, 0, 0));
        sexoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        sexoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sexoComboBoxActionPerformed(evt);
            }
        });
        P13.add(sexoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 170, -1));

        javax.swing.GroupLayout P1Layout = new javax.swing.GroupLayout(P1);
        P1.setLayout(P1Layout);
        P1Layout.setHorizontalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(P13, javax.swing.GroupLayout.PREFERRED_SIZE, 3470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        P1Layout.setVerticalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(P13, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Paineis.addTab("tab5", P1);

        P2.setBackground(new java.awt.Color(0, 204, 204));
        P2.setForeground(new java.awt.Color(0, 0, 0));
        P2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("CADASTRO DE FUNCIONÁRIOS");
        P2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 6, -1, 38));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Sexo");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jTextFieldNomeCompleto.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldNomeCompleto.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldNomeCompleto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jTextFieldNomeCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldNomeCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeCompletoActionPerformed(evt);
            }
        });
        P2.add(jTextFieldNomeCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 164, 24));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Email");
        P2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Cargo");
        P2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jTextFieldEmail.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldEmail.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldEmail.setBorder(null);
        P2.add(jTextFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 160, 22));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Contacto");
        P2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, -1, -1));

        jTextFieldContacto.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldContacto.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldContacto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        P2.add(jTextFieldContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 160, 22));

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton10.setForeground(new java.awt.Color(0, 0, 0));
        jButton10.setText("Adicionar");
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        P2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(0, 0, 0));
        jButton13.setText("Detalhes do Essenciais");
        jButton13.setBorder(null);
        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        P2.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 270, -1, 20));

        jButtonEditar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonEditar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEditar.setText("Editar");
        jButtonEditar.setBorderPainted(false);
        jButtonEditar.setContentAreaFilled(false);
        jButtonEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });
        P2.add(jButtonEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, -1, -1));

        jButtonSalvarEdicao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSalvarEdicao.setForeground(new java.awt.Color(0, 0, 0));
        jButtonSalvarEdicao.setText("Salvar");
        jButtonSalvarEdicao.setBorderPainted(false);
        jButtonSalvarEdicao.setContentAreaFilled(false);
        jButtonSalvarEdicao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSalvarEdicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarEdicaoActionPerformed(evt);
            }
        });
        P2.add(jButtonSalvarEdicao, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, -1, -1));

        P11.setBackground(new java.awt.Color(0, 51, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Detalhes do Funcionario");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Horario de Entrada");

        jComboBoxHoraEntrada.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxHoraEntrada.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxHoraEntrada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "06:00h", "07:00h", "08:00h", "09:00h", "10:00h", "11:00h", "12:00h", "13:00h" }));
        jComboBoxHoraEntrada.setBorder(null);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Horario de Saida");

        jComboBoxHoraSaida.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxHoraSaida.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxHoraSaida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "14:00h", "15:00h", "16:00h", "17:00h", "18:00h", "19:00h", "20:00h", "21:00h" }));
        jComboBoxHoraSaida.setBorder(null);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Salario");

        jTextFieldSalario.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldSalario.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldSalario.setBorder(null);

        javax.swing.GroupLayout P11Layout = new javax.swing.GroupLayout(P11);
        P11.setLayout(P11Layout);
        P11Layout.setHorizontalGroup(
            P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P11Layout.createSequentialGroup()
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(P11Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(52, 52, 52)
                                .addComponent(jComboBoxHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(P11Layout.createSequentialGroup()
                                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldSalario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxHoraSaida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(P11Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        P11Layout.setVerticalGroup(
            P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxHoraSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        P2.add(P11, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, -1, 177));

        jTableFuncionarios.setBackground(new java.awt.Color(0, 51, 51));
        jTableFuncionarios.setForeground(new java.awt.Color(255, 255, 255));
        jTableFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Sexo", "Email", "Cargo", "Morada", "Contacto", "Salário", "Hora de Entrada", "Horário de Saída"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableFuncionarios.setColumnSelectionAllowed(true);
        jTableFuncionarios.setGridColor(new java.awt.Color(0, 0, 0));
        jTableFuncionarios.setShowGrid(true);
        jScrollPane2.setViewportView(jTableFuncionarios);
        jTableFuncionarios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        P2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 1000, 291));

        jComboBoxCargo.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxCargo.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supervisor", "Operador de Máquina", "Auxiliar de Limpeza", "Atendente", "Recepcionista", "Lavador de Carros", "Técnico de Manutenção", " ", " " }));
        jComboBoxCargo.setBorder(null);
        P2.add(jComboBoxCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 164, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Morada");
        P2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jTextFieldMorada.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMorada.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldMorada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jTextFieldMorada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMoradaActionPerformed(evt);
            }
        });
        P2.add(jTextFieldMorada, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 164, -1));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Nome");
        P2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        jComboBoxSexo.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSexo.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        jComboBoxSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSexoActionPerformed(evt);
            }
        });
        P2.add(jComboBoxSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 164, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Senha");
        P2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 40, -1));

        jTextFieldSenha.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldSenha.setForeground(new java.awt.Color(0, 0, 0));
        P2.add(jTextFieldSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 160, -1));

        Paineis.addTab("tab6", P2);

        P8.setBackground(new java.awt.Color(0, 153, 153));
        P8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableAgendamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "marca", "placa", "proprietario", "contacto", "tipo_viatura", "hora_entrada", "hora_saida", "total_servicos"
            }
        ));
        jScrollPane1.setViewportView(jTableAgendamentos);

        P8.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 930, -1));

        jButton5.setText("Editar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        P8.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 580, -1, -1));

        jButtonSalvarEdicao2.setText("Salvar");
        jButtonSalvarEdicao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarEdicao2ActionPerformed(evt);
            }
        });
        P8.add(jButtonSalvarEdicao2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 580, -1, -1));

        jButtonRemover.setText("Remover");
        jButtonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });
        P8.add(jButtonRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 580, -1, -1));

        jButtonFeito.setText("Feito");
        jButtonFeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFeitoActionPerformed(evt);
            }
        });
        P8.add(jButtonFeito, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 580, 60, -1));

        Paineis.addTab("tab8", P8);

        P9.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout P9Layout = new javax.swing.GroupLayout(P9);
        P9.setLayout(P9Layout);
        P9Layout.setHorizontalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1670, Short.MAX_VALUE)
        );
        P9Layout.setVerticalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
        );

        Paineis.addTab("tab9", P9);

        P12.setBackground(new java.awt.Color(51, 51, 51));

        jTableFeitos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "marca", "placa", "proprietario", "contacto", "tipo_viatura", "hora_entrada", "hora_saida", "total_servicos"
            }
        ));
        jScrollPane3.setViewportView(jTableFeitos);

        javax.swing.GroupLayout P12Layout = new javax.swing.GroupLayout(P12);
        P12.setLayout(P12Layout);
        P12Layout.setHorizontalGroup(
            P12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P12Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(648, Short.MAX_VALUE))
        );
        P12Layout.setVerticalGroup(
            P12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P12Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        Paineis.addTab("tab5", P12);

        P4.setBackground(new java.awt.Color(255, 0, 204));

        javax.swing.GroupLayout P4Layout = new javax.swing.GroupLayout(P4);
        P4.setLayout(P4Layout);
        P4Layout.setHorizontalGroup(
            P4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1670, Short.MAX_VALUE)
        );
        P4Layout.setVerticalGroup(
            P4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
        );

        Paineis.addTab("tab7", P4);

        P10.setBackground(new java.awt.Color(18, 181, 185));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel27.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Clientes Cadastrados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(jLabel27)
                .addContainerGap(599, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableClientes.setBackground(new java.awt.Color(204, 255, 255));
        jTableClientes.setForeground(new java.awt.Color(0, 0, 0));
        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "nome", "sexo", "email", "contacto", "morada", "marca", "modelo", "matricula", "status"
            }
        ));
        jTableClientes.setSelectionBackground(new java.awt.Color(0, 153, 204));
        jScrollPane4.setViewportView(jTableClientes);

        jButtonEditar1.setBackground(new java.awt.Color(51, 255, 51));
        jButtonEditar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonEditar1.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEditar1.setText("Editar");
        jButtonEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditar1ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(255, 0, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(0, 0, 0));
        jButton12.setText("Excluir");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Pesquisar:");

        pesquisajTextField.setBackground(new java.awt.Color(255, 255, 255));
        pesquisajTextField.setForeground(new java.awt.Color(0, 0, 0));
        pesquisajTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisajTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout P10Layout = new javax.swing.GroupLayout(P10);
        P10.setLayout(P10Layout);
        P10Layout.setHorizontalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P10Layout.createSequentialGroup()
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(jButtonEditar1)
                        .addGap(93, 93, 93)
                        .addComponent(jButton12))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1057, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(434, Short.MAX_VALUE))
        );
        P10Layout.setVerticalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(pesquisajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEditar1)
                    .addComponent(jButton12))
                .addGap(77, 77, 77))
        );

        Paineis.addTab("tab10", P10);

        P16.setBackground(new java.awt.Color(153, 255, 255));
        P16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        P16.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 24, -1, 41));

        jLabel70.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("Absa CarWash");
        P16.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 24, -1, -1));

        jLabel71.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setText("Gestão Financeira");
        P16.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 49, -1, -1));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Tabela de preços");
        P16.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(521, 64, -1, -1));

        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel72MouseClicked(evt);
            }
        });
        P16.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(1203, 12, -1, 40));

        jLabel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel73MouseClicked(evt);
            }
        });
        P16.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(1101, 12, -1, -1));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        P16.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 1070, 10));

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Ligeiro");
        P16.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 140, -1, -1));
        P16.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 135, -1, -1));

        jLabel77.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("Lavagem Interna");
        P16.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 181, -1, -1));
        P16.add(jTextFieldLavagemInterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 209, 69, 24));

        jLabel78.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 0));
        jLabel78.setText("MZN");
        P16.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 217, -1, -1));

        jLabel79.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setText("Lavagem Completa");
        P16.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, -1, -1));
        P16.add(jTextFieldLavagemCompleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, 80, 24));

        jLabel80.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setText("MZN");
        P16.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, -1, -1));
        P16.add(jTextFieldLavagemRodasPneus, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 210, 110, 24));

        jLabel81.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setText("Lavagem de Rodas e Pneus");
        P16.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, -1, -1));

        jLabel82.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 0));
        jLabel82.setText("MZN");
        P16.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 210, -1, -1));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        P16.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 1070, 10));

        jLabel83.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 0));
        jLabel83.setText("Medio");
        P16.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 305, -1, -1));
        P16.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 304, -1, -1));

        jLabel85.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("Pesado");
        P16.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 479, -1, -1));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        P16.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 1070, 10));
        P16.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 478, -1, -1));

        jButton19.setBackground(new java.awt.Color(51, 51, 51));
        jButton19.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Atualizar");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        P16.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 170, -1, -1));

        jButton20.setBackground(new java.awt.Color(51, 51, 51));
        jButton20.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setText("Atualizar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        P16.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 340, -1, -1));

        jButton21.setBackground(new java.awt.Color(51, 51, 51));
        jButton21.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Atualizar");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        P16.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 550, -1, -1));

        jLabel93.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 0, 0));
        jLabel93.setText("Lavagem Externa");
        P16.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, -1, -1));
        P16.add(jTextFieldLavagemExterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 69, 24));

        jLabel94.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 0, 0));
        jLabel94.setText("MZN");
        P16.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, -1, -1));

        jLabel95.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 0));
        jLabel95.setText("Polimento e Encerramento");
        P16.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 180, -1, -1));
        P16.add(jTextFieldPolimentoEnceramento, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 69, 24));

        jLabel96.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(0, 0, 0));
        jLabel96.setText("MZN");
        P16.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, -1, -1));
        P16.add(jTextFieldLavagemExterna2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 69, 24));
        P16.add(jTextFieldLavagemInterna2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 69, 24));

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("MZN");
        P16.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, -1, -1));

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("MZN");
        P16.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, -1, -1));

        jLabel46.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("MZN");
        P16.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 400, -1, -1));
        P16.add(jTextFieldLavagemCompleta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 400, 80, 24));

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Lavagem Completa");
        P16.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, -1, -1));

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("MZN");
        P16.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, -1, -1));
        P16.add(jTextFieldPolimentoEnceramento2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 80, 24));

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("Polimento e Encerramento");
        P16.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, -1, -1));
        P16.add(jTextFieldLavagemRodasPneus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, 110, 24));

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("MZN");
        P16.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, -1, -1));

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Lavagem de Rodas e Pneus");
        P16.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, -1, -1));

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Lavagem Interna");
        P16.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, -1));

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("Lavagem Externa");
        P16.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 370, -1, -1));

        jLabel87.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 0));
        jLabel87.setText("Lavagem Interna");
        P16.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 550, -1, -1));

        jLabel88.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("MZN");
        P16.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, -1, -1));
        P16.add(jTextFieldLavagemInterna3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 580, 69, 24));

        jLabel89.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 0, 0));
        jLabel89.setText("MZN");
        P16.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 580, -1, -1));

        jLabel90.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 0, 0));
        jLabel90.setText("Lavagem Externa");
        P16.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, -1, -1));
        P16.add(jTextFieldLavagemExterna3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 69, 24));

        jLabel91.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 0, 0));
        jLabel91.setText("MZN");
        P16.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, -1, -1));

        jLabel92.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 0, 0));
        jLabel92.setText("Lavagem Completa");
        P16.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 550, -1, -1));
        P16.add(jTextFieldLavagemCompleta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 580, 80, 24));

        jLabel97.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(0, 0, 0));
        jLabel97.setText("Polimento e Encerramento");
        P16.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, -1, -1));

        jLabel98.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(0, 0, 0));
        jLabel98.setText("MZN");
        P16.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 590, -1, -1));
        P16.add(jTextFieldPolimentoEnceramento3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 580, 80, 24));

        jLabel99.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(0, 0, 0));
        jLabel99.setText("Lavagem de Rodas e Pneus");
        P16.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, -1, -1));

        jLabel100.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(0, 0, 0));
        jLabel100.setText("MZN");
        P16.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 580, -1, -1));
        P16.add(jTextFieldLavagemRodasPneus3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 580, 110, 24));

        Paineis.addTab("tab2", P16);

        getContentPane().add(Paineis, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, -30, 1670, 720));

        setSize(new java.awt.Dimension(1343, 705));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P2);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P3);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P10);
        
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
Paineis.setSelectedComponent(P5);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableFuncionarios.getSelectedRow();
    if (selectedRow != -1) {
        // Preencher os campos com os dados da linha selecionada
        jTextFieldNomeCompleto.setText(jTableFuncionarios.getValueAt(selectedRow, 0).toString());
        jComboBoxSexo.setSelectedItem(jTableFuncionarios.getValueAt(selectedRow, 1).toString());
        jTextFieldEmail.setText(jTableFuncionarios.getValueAt(selectedRow, 2).toString());
        jComboBoxCargo.setSelectedItem(jTableFuncionarios.getValueAt(selectedRow, 3).toString());
        jTextFieldMorada.setText(jTableFuncionarios.getValueAt(selectedRow, 4).toString());
        jTextFieldContacto.setText(jTableFuncionarios.getValueAt(selectedRow, 5).toString());
        jTextFieldSalario.setText(jTableFuncionarios.getValueAt(selectedRow, 6).toString());
        jComboBoxHoraEntrada.setSelectedItem(jTableFuncionarios.getValueAt(selectedRow, 7).toString());
        jComboBoxHoraSaida.setSelectedItem(jTableFuncionarios.getValueAt(selectedRow, 8).toString());

        // Atualizar os dados no banco após a edição
        jButtonSalvarEdicao.setEnabled(true);
    } else {
        JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.");
    }
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P7);
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P8);
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P9);
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
                                      
    // Pegando os valores dos campos do formulário
String nomeCompleto = jTextFieldNomeCompleto.getText();
String sexo = jComboBoxSexo.getSelectedItem().toString();  
String email = jTextFieldEmail.getText();
String senha = jTextFieldSenha.getText();
String cargo = jComboBoxCargo.getSelectedItem().toString();  
String morada = jTextFieldMorada.getText();
String contacto = jTextFieldContacto.getText();
String salario = jTextFieldSalario.getText();  

String horaEntrada = jComboBoxHoraEntrada.getSelectedItem().toString().replace("h", "") + ":00";  
String horaSaida = jComboBoxHoraSaida.getSelectedItem().toString().replace("h", "") + ":00"; 

if (nomeCompleto.isEmpty() || email.isEmpty() || senha.isEmpty() || contacto.isEmpty() || salario.isEmpty() || horaEntrada.isEmpty() || horaSaida.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios!");
} else {
    try {
        Double salarioNumerico = Double.parseDouble(salario);

        
        java.sql.Connection con = Conexao.connect(); 

        
        String sql = "INSERT INTO funcionarios (nome_completo, sexo, email, senha, cargo, morada, contacto, salario, hora_entrada, hora_saida) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Preparando a declaração
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, nomeCompleto);
        pst.setString(2, sexo);
        pst.setString(3, email);
        pst.setString(4, senha);
        pst.setString(5, cargo);
        pst.setString(6, morada);
        pst.setString(7, contacto);
        pst.setDouble(8, salarioNumerico);  
        pst.setString(9, horaEntrada);
        pst.setString(10, horaSaida);

        int result = pst.executeUpdate();

        // Verificação da inserção dos dados 
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Funcionário adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao adicionar funcionário.");
        }

        pst.close();
        con.close();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido para o salário.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao adicionar funcionário: " + e.getMessage());
    }
}
                     

       
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButtonSalvarEdicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarEdicaoActionPerformed
   
    String idFuncionarioStr = jTextFieldId.getText();  // Pegue o valor do campo ID
    System.out.println("ID do funcionário selecionado: " + idFuncionarioStr);  

    if (idFuncionarioStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Erro: ID do funcionário não encontrado. Por favor, selecione um funcionário para editar.");
        return;  // Interrompe a execução do método se o ID estiver vazio
    }

    try {
        // Convertendo o ID para inteiro (certifique-se de que jTextFieldId contenha um número válido)
        int idFuncionario = Integer.parseInt(idFuncionarioStr);

        // Pegando os valores dos campos do formulário
        String nomeCompleto = jTextFieldNomeCompleto.getText();
        String sexo = jComboBoxSexo.getSelectedItem().toString();  
        String email = jTextFieldEmail.getText();
        String cargo = jComboBoxCargo.getSelectedItem().toString();  
        String morada = jTextFieldMorada.getText();
        String contacto = jTextFieldContacto.getText();
        String salario = jTextFieldSalario.getText();  
        String horaEntrada = jComboBoxHoraEntrada.getSelectedItem().toString();  
        String horaSaida = jComboBoxHoraSaida.getSelectedItem().toString();  

        if (nomeCompleto.isEmpty() || email.isEmpty() || contacto.isEmpty() || salario.isEmpty() || horaEntrada.isEmpty() || horaSaida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios!");
        } else {
            
            java.sql.Connection con = Conexao.connect();

            
            String sql = "UPDATE funcionarios SET nome_completo = ?, sexo = ?, email = ?, cargo = ?, morada = ?, contacto = ?, salario = ?, hora_entrada = ?, hora_saida = ? WHERE id = ?";

            // Prepara a declaração
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nomeCompleto);
            pst.setString(2, sexo);
            pst.setString(3, email);
            pst.setString(4, cargo);
            pst.setString(5, morada);
            pst.setString(6, contacto);
            pst.setString(7, salario);
            pst.setString(8, horaEntrada);
            pst.setString(9, horaSaida);
            pst.setInt(10, idFuncionario); 

            // Executa a atualização no banco de dados
            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar os dados do funcionário.");
            }

            
            pst.close();
            con.close();
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Erro: ID do funcionário inválido.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao atualizar o funcionário: " + e.getMessage());
    }
    
    }//GEN-LAST:event_jButtonSalvarEdicaoActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
         P11.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        // TODO add your handling code here:
         P11.setVisible(true);
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextFieldMoradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMoradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMoradaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextFieldNomeCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeCompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeCompletoActionPerformed

    private void jComboBoxSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSexoActionPerformed

    private void jButtonSalvarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAgendamentoActionPerformed
        // TODO add your handling code here:
                                                     
   // Obtenha os valores dos campos
    String marca = jTextFieldMarca.getText();
    String placa = jTextFieldPlaca.getText();
    String proprietario = jTextFieldProprietario.getText();
    String contacto = jTextFieldContacto2.getText();
    String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();
    String horaEntrada = jTextFieldHoraEntrada.getText();
    String horaSaida = jTextFieldHoraSaida.getText();

    
    // Verifique se todos os campos foram preenchidos corretamente
    if (marca.isEmpty() || placa.isEmpty() || proprietario.isEmpty() || contacto.isEmpty() || tipoViatura.isEmpty() || horaEntrada.isEmpty() || horaSaida.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
        return;
    }

    // Tente converter o campo "total" para um número
    double total = 0;
    try {
        total = Double.parseDouble(jTextFieldTotal.getText());
        
if (total <= 0) {
            JOptionPane.showMessageDialog(
            
this, "Selecione pelo menos um serviço.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Erro no valor total. Verifique se o valor está correto.");
        return;
    }

    // Inserção no banco de dados
    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "INSERT INTO agendamentos (marca, placa, proprietario, contacto, tipo_viatura, hora_entrada, hora_saida, total_servicos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, marca);
        pst.setString(2, placa);
        pst.setString(3, proprietario);
        pst.setString(4, contacto);
        pst.setString(5, tipoViatura);
        pst.setString(6, horaEntrada);
        pst.setString(7, horaSaida);
        pst.setDouble(8, total);

        pst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Agendamento salvo com sucesso!");

        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar agendamento: " + e.getMessage());
    }



    }//GEN-LAST:event_jButtonSalvarAgendamentoActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        carregarAgendamentos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void P6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_P6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_P6MouseClicked

    private void jButtonSalvarEdicao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarEdicao2ActionPerformed
        // TODO add your handling code here:
   int selectedRow = jTableAgendamentos.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um agendamento para editar.");
        return;
    }

    // Obtenha os valores dos campos da interface
    String id = jTextFieldId.getText();
    String marca = jTextFieldMarca.getText();
    String placa = jTextFieldPlaca.getText();
    String proprietario = jTextFieldProprietario.getText();
    String contacto = jTextFieldContacto2.getText();
    String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();
    String horaEntrada = jTextFieldHoraEntrada.getText(); // Hora é string, sem conversão
    String horaSaida = jTextFieldHoraSaida.getText(); // Hora é string, sem conversão
    String totalText = jTextFieldTotal.getText();
    
    
    System.out.println("Marca: " + marca);
System.out.println("Placa: " + placa);
System.out.println("Proprietário: " + proprietario);
System.out.println("Contacto: " + contacto);
System.out.println("Tipo de Viatura: " + tipoViatura);
System.out.println("Hora de Entrada: " + horaEntrada);
System.out.println("Hora de Saída: " + horaSaida);

    // Verifique se todos os campos foram preenchidos corretamente
    if (marca.isEmpty() || placa.isEmpty() || proprietario.isEmpty() || contacto.isEmpty() || tipoViatura.isEmpty() || horaEntrada.isEmpty() || horaSaida.isEmpty() || totalText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
        return;
    }

    // Converter total para double e validar possíveis erros de formatação
    double total;
    try {
        total = Double.parseDouble(totalText); // Total pode ser double
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Formato inválido para o campo Total.");
        return;
    }

    // Atualize o registro no banco de dados
    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "UPDATE agendamentos SET marca = ?, placa = ?, proprietario = ?, contacto = ?, tipo_viatura = ?, hora_entrada = ?, hora_saida = ?, total_servicos = ? WHERE id = ?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, marca);
        pst.setString(2, placa);
        pst.setString(3, proprietario);
        pst.setString(4, contacto);
        pst.setString(5, tipoViatura);
        pst.setString(6, horaEntrada); // Trate como string, sem conversão
        pst.setString(7, horaSaida); // Trate como string, sem conversão
        pst.setDouble(8, total); // Campo total é numérico
        pst.setInt(9, Integer.parseInt(id));  // O ID precisa ser passado para identificar o registro a ser editado

        // Executa o update
        int updatedRows = pst.executeUpdate();
        if (updatedRows > 0) {
            JOptionPane.showMessageDialog(this, "Edição salva com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Nenhuma linha foi atualizada. Verifique o ID.");
        }

        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar edição: " + e.getMessage());
    }
    }//GEN-LAST:event_jButtonSalvarEdicao2ActionPerformed

    private void jButtonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoverActionPerformed
        // TODO add your handling code here:
      int selectedRow = jTableAgendamentos.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um agendamento para remover.");
        return;
    }

    // Obtenha o ID do agendamento selecionado
    int id = (int) jTableAgendamentos.getValueAt(selectedRow, 0); // O ID está na primeira coluna

    // Remover do banco de dados
    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "DELETE FROM agendamentos WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Agendamento removido com sucesso!");

        pst.close();
        con.close();

        // Atualizar a tabela
        carregarAgendamentos(); // Atualiza a tabela após a remoção
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao remover agendamento: " + e.getMessage());
    }
    }//GEN-LAST:event_jButtonRemoverActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
         int selectedRow = jTableAgendamentos.getSelectedRow(); // Verifica se há uma linha selecionada
    
    if (selectedRow != -1) { // Se uma linha foi selecionada
        // Preencher os campos com os valores da linha selecionada
        jTextFieldMarca.setText(jTableAgendamentos.getValueAt(selectedRow, 0).toString());
        jTextFieldPlaca.setText(jTableAgendamentos.getValueAt(selectedRow, 1).toString());
        jTextFieldProprietario.setText(jTableAgendamentos.getValueAt(selectedRow, 2).toString());
        jTextFieldContacto.setText(jTableAgendamentos.getValueAt(selectedRow, 3).toString());
        jComboBoxTipoViatura.setSelectedItem(jTableAgendamentos.getValueAt(selectedRow, 4).toString());
        jTextFieldHoraEntrada.setText(jTableAgendamentos.getValueAt(selectedRow, 5).toString());
        jTextFieldHoraSaida.setText(jTableAgendamentos.getValueAt(selectedRow, 6).toString());
        jTextFieldTotal.setText(jTableAgendamentos.getValueAt(selectedRow, 7).toString());
        
        // Ativa o botão "Salvar" para permitir salvar as edições
        jButtonSalvarEdicao2.setEnabled(true);
        
    } else {
        // Mostra uma mensagem caso nenhum agendamento tenha sido selecionado
        JOptionPane.showMessageDialog(this, "Selecione um agendamento para editar.");
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P12);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButtonFeitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFeitoActionPerformed
        // TODO add your handling code here:
                                                  
    int selectedRow = jTableAgendamentos.getSelectedRow();

    if (selectedRow != -1) {
        DefaultTableModel modelPendentes = (DefaultTableModel) jTableAgendamentos.getModel();
        String serviceId = modelPendentes.getValueAt(selectedRow, 0).toString();  // ID na primeira coluna

        // Pega os dados da linha selecionada
        Object[] rowData = new Object[jTableAgendamentos.getColumnCount()];
        for (int i = 0; i < jTableAgendamentos.getColumnCount(); i++) {
            rowData[i] = modelPendentes.getValueAt(selectedRow, i);
        }

        try {
            java.sql.Connection con = Conexao.connect(); // Conecta ao banco de dados
            
            // 1. Remove o serviço da tabela de agendamentos pendentes
            String sqlDelete = "DELETE FROM agendamentos WHERE id = ?";
            PreparedStatement pstDelete = con.prepareStatement(sqlDelete);
            pstDelete.setString(1, serviceId);
            int affectedRows = pstDelete.executeUpdate();

            if (affectedRows > 0) {
                // 2. Insere o serviço na tabela "agendamentos_feitos"
                String sqlInsert = "INSERT INTO agendamentos_feitos (id, marca, placa, proprietario, contacto, tipo_viatura, hora_entrada, hora_saida, total_servicos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstInsert = con.prepareStatement(sqlInsert);
                for (int i = 0; i < rowData.length; i++) {
                    pstInsert.setObject(i + 1, rowData[i]);
                }

                pstInsert.executeUpdate();

                // Remove a linha da JTable pendentes
                modelPendentes.removeRow(selectedRow);

                // Adiciona a linha à JTableFeitos
                DefaultTableModel modelFeitos = (DefaultTableModel) jTableFeitos.getModel();
                modelFeitos.addRow(rowData);

                JOptionPane.showMessageDialog(this, "Serviço marcado como 'Feito' com sucesso e movido para a tabela de serviços concluídos!");
                
                pstInsert.close();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao remover o serviço da tabela pendente!");
            }

            pstDelete.close();
            con.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, selecione um serviço na tabela.");
    }
    }//GEN-LAST:event_jButtonFeitoActionPerformed

    private void jButtonEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditar1ActionPerformed
        int selectedRow = jTableClientes.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = jTableClientes.convertRowIndexToModel(selectedRow);

            // Ajuste: Pega o ID da coluna 0 e o nome da coluna correta (assumindo coluna 1)
            String nome = (String) jTableClientes.getValueAt(modelRow, 1); // Pega o nome da coluna 1
            String sexo = (String) jTableClientes.getValueAt(modelRow, 2); // Ajuste para a coluna correta
            String email = (String) jTableClientes.getValueAt(modelRow, 3);
            String contacto = (String) jTableClientes.getValueAt(modelRow, 4);
            String morada = (String) jTableClientes.getValueAt(modelRow, 5);
            String marca = (String) jTableClientes.getValueAt(modelRow, 6);
            String modelo = (String) jTableClientes.getValueAt(modelRow, 7);
            String matricula = (String) jTableClientes.getValueAt(modelRow, 8);
            String status = (String) jTableClientes.getValueAt(modelRow, 9);

            // Configura os dados no painel de cadastro
            setClienteData(nome, sexo, email, contacto, morada, marca, modelo, matricula, status);

            // Muda para o painel de cadastro (P2)
            Paineis.setSelectedComponent(P1);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente para editar.");
        }
        }

        // Método para configurar os dados do cliente no painel de cadastro
        private void setClienteData(String nome, String sexo, String email, String contacto, String morada, String marca, String modelo, String matricula, String status) {
            // Configura os campos no painel de cadastro, verificando se os valores não são nulos
            nomeField.setText(nome != null ? nome : "");
            sexoComboBox.setSelectedItem(sexo != null ? sexo : "");
            emailField.setText(email != null ? email : "");
            contactoField.setText(contacto != null ? contacto : "");
            moradaField.setText(morada != null ? morada : "");
            marcaField.setText(marca != null ? marca : "");
            modeloField.setText(modelo != null ? modelo : "");
            jTextFieldMatricula.setText(matricula != null ? matricula : "");
            statusComboBox.setSelectedItem(status != null ? status : "");
    }//GEN-LAST:event_jButtonEditar1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        int selectedRow = jTableClientes.getSelectedRow(); // Pega a linha selecionada

        // Verifica se alguma linha foi selecionada
        if (selectedRow != -1) {
            // Supondo que o ID esteja na primeira coluna (coluna 0) e seja um Integer
            Object idObj = jTableClientes.getValueAt(selectedRow, 0);

            if (idObj instanceof Integer) {
                int clienteId = (Integer) idObj;

                // Agora você pode usar o clienteId para excluir o cliente do banco de dados
                try {
                    java.sql.Connection con = Conexao.connect();
                    String sql = "DELETE FROM clientes WHERE id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, clienteId);

                    int linhasAfetadas = pst.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                        // Remove a linha da JTable
                        DefaultTableModel model = (DefaultTableModel) jTableClientes.getModel();
                        model.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir cliente.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    pst.close();
                    con.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao recuperar o ID do cliente.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para excluir.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void pesquisajTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisajTextFieldActionPerformed
        // Função para pesquisa na tabela de clientes
        String pesquisa = pesquisajTextField.getText().trim().toLowerCase(); // Obter texto da pesquisa
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableClientes.getModel());
        jTableClientes.setRowSorter(sorter);

        try {
            if (pesquisa.isEmpty()) {
                sorter.setRowFilter(null); // Mostra todas as linhas se o campo de pesquisa estiver vazio
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + pesquisa)); // Aplica o filtro baseado na pesquisa, ignorando maiúsculas/minúsculas
            }
        } catch (PatternSyntaxException e) {
            JOptionPane.showMessageDialog(null, "Formato de pesquisa inválido.", "Erro de Pesquisa", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_pesquisajTextFieldActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
       Paineis.setSelectedComponent(P1);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void emailFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailFieldKeyTyped
        // Obter o texto atual do campo de e-mail
        String email = emailField.getText().trim();

        // Expressão regular para validar o formato do e-mail
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        // Verificar se o e-mail corresponde ao padrão
        if (!email.matches(emailPattern)) {
            // Exibir mensagem de erro se o formato estiver incorreto
            emailField.setForeground(java.awt.Color.RED);
            emailField.setToolTipText("Email inválido. Formato esperado: example@gmail.com");
        } else {
            // Se o formato estiver correto, resetar a cor para a padrão
            emailField.setForeground(java.awt.Color.BLACK);
            emailField.setToolTipText(null);
        }
    }//GEN-LAST:event_emailFieldKeyTyped

    private void CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastrarActionPerformed
        // Obter os dados dos campos
        String nome = nomeField.getText().trim();
        String sexo = (String) sexoComboBox.getSelectedItem();
        String email = emailField.getText().trim();
        String morada = moradaField.getText().trim();
        String contacto = contactoField.getText().trim();
        String marca = marcaField.getText().trim();
        String modelo = modeloField.getText().trim();
        String matricula = jTextFieldMatricula.getText().trim();
        String status = statusComboBox.getSelectedItem().toString().toLowerCase(); // Converte para minúsculo

        // Validação dos campos obrigatórios
        if (nome.isEmpty() || sexo == null || email.isEmpty() || morada.isEmpty() || contacto.isEmpty() ||
            marca.isEmpty() || modelo.isEmpty() || matricula.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.",
                "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação do formato da matrícula
        if (!matricula.matches("[A-Z]{3}-\\d{3}-[A-Z]{2}")) {
            JOptionPane.showMessageDialog(null, "Placa inválida. Formato esperado: ABC-123-XY.",
                "Formato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se o status é válido
        if (!status.equals("ativo") && !status.equals("inativo")) {
            JOptionPane.showMessageDialog(null, "Status inválido. Os valores permitidos são 'ativo' ou 'inativo'.",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            java.sql.Connection con = Conexao.connect();

            // Verificar se o cliente já está cadastrado com base no email, contacto ou matrícula
            String sql = "SELECT COUNT(*) FROM clientes WHERE email = ? OR contacto = ? OR matricula = ?";
            PreparedStatement pst = con.prepareStatement (sql);
            pst.setString(1, email);
            pst.setString(2, contacto);
            pst.setString(3, matricula);
            try (ResultSet resultado = pst.executeQuery()) {
                resultado.next();
                int existeCliente = resultado.getInt(1);

                if (existeCliente > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente já cadastrado com este email, contacto ou matrícula.",
                        "Cadastro duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // SQL para inserir o registro
            sql = "INSERT INTO clientes (nome, sexo, email, contacto, morada, marca, modelo, matricula, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, sexo);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, contacto);
                preparedStatement.setString(5, morada);
                preparedStatement.setString(6, marca);
                preparedStatement.setString(7, modelo);
                preparedStatement.setString(8, matricula);
                preparedStatement.setString(9, status);

                // Executar a inserção
                int linhasAfetadas = preparedStatement.executeUpdate();
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    DefaultTableModel model = (DefaultTableModel) jTableClientes.getModel();

                    // Adiciona o novo cliente à tabela
                    model.addRow(new Object[] { nome, sexo, email, contacto, morada, marca, modelo, matricula, status });

                    // Limpar os campos de entrada
                    nomeField.setText("");
                    sexoComboBox.setSelectedIndex(0);
                    emailField.setText("");
                    contactoField.setText("");
                    moradaField.setText("");
                    marcaField.setText("");
                    modeloField.setText("");
                    jTextFieldMatricula.setText("");
                    statusComboBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao realizar o cadastro.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_CadastrarActionPerformed

    private void jTextFieldMatriculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMatriculaKeyTyped
        // Obter o texto atual no campo de matrícula
        String matricula = jTextFieldMatricula.getText();

        // Verificar o comprimento máximo permitido
        if (matricula.length() >= 10) { // 10 é o comprimento máximo de "ABC-123-XY"
            evt.consume(); // Cancelar o evento se o comprimento máximo for atingido
            JOptionPane.showMessageDialog(null, "Limite de caracteres atingido. O formato esperado é ABC-123-XY.",
                "Validação de Matrícula", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar o formato da matrícula
        if (!matricula.matches("[A-Z]{3}-\\d{3}-[A-Z]{2}")) {
            jTextFieldMatricula.setForeground(java.awt.Color.BLACK); // Mudar a cor do texto para vermelho se não corresponder
            // Opcional: você pode exibir uma mensagem aqui também
        } else {
            jTextFieldMatricula.setForeground(java.awt.Color.RED); // Mudar a cor do texto para preto se corresponder
        }
    }//GEN-LAST:event_jTextFieldMatriculaKeyTyped

    private void contactoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactoFieldActionPerformed

    private void contactoFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactoFieldKeyTyped
        // Obter o texto atual no campo de contato
        String contacto = contactoField.getText();

        // Verificar o comprimento máximo permitido (por exemplo, 9 dígitos)
        if (contacto.length() >= 9) { // Supondo que o limite é 9 dígitos
            evt.consume(); // Cancelar o evento se o comprimento máximo for atingido
            JOptionPane.showMessageDialog(null, "Limite de caracteres atingido. O número de contato deve ter 9 dígitos.",
                "Validação de Contato", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se o comprimento do texto atual for 1 ou 2, permitir qualquer dígito
        if (contacto.length() < 2) {
            return; // Permitir que o usuário digite os primeiros dois dígitos
        }

        // Validar os primeiros dois dígitos
        String prefixo = contacto.substring(0, 2);
        if (contacto.length() == 2 && !prefixo.matches("82|83|84|85|86|87")) {
            contactoField.setForeground(java.awt.Color.RED); // Mudar a cor do texto para vermelho se não corresponder
            evt.consume(); // Cancelar o evento se os dois primeiros dígitos não forem válidos
            JOptionPane.showMessageDialog(null, "Os dois primeiros dígitos devem ser 82, 83, 84, 85, 86 ou 87.",
                "Validação de Contato", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se a validação passar, você pode definir a cor do texto para indicar que está tudo certo
        contactoField.setForeground(java.awt.Color.BLACK); // Mudar a cor do texto para preto
    }//GEN-LAST:event_contactoFieldKeyTyped

    private void sexoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sexoComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sexoComboBoxActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P16);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jLabel73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel73MouseClicked

    private void jLabel72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel72MouseClicked

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
   // Conexão com o banco de dados
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testesdb", "root", "")) {
        // Inicia uma transação
        conn.setAutoCommit(false);

        // Consulta SQL para atualizar o preço do serviço
        String sql = "UPDATE preco_ligeiro SET preco = ? WHERE servico = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Configurando os preços dos serviços a partir dos JTextFields

            // Lavagem Interna
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemInterna.getText()));
            pstmt.setString(2, "Lavagem Interna");
            pstmt.executeUpdate();

            // Lavagem Externa
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemExterna.getText()));
            pstmt.setString(2, "Lavagem Externa");
            pstmt.executeUpdate();

            // Lavagem Completa
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemCompleta.getText()));
            pstmt.setString(2, "Lavagem Completa");
            pstmt.executeUpdate();

            // Polimento e Enceramento
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldPolimentoEnceramento.getText()));
            pstmt.setString(2, "Polimento e Enceramento");
            pstmt.executeUpdate();

            // Lavagem de Rodas e Pneus
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemRodasPneus.getText()));
            pstmt.setString(2, "Lavagem de Rodas e Pneus");
            pstmt.executeUpdate();
        }

        // Confirma a transação
        conn.commit();
        JOptionPane.showMessageDialog(this, "Preços atualizados com sucesso!");

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao atualizar os preços.");
    }
        
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testesdb", "root", "")) {
        // Inicia uma transação
        conn.setAutoCommit(false);

        // Consulta SQL para atualizar o preço do serviço
        String sql = "UPDATE preco_medio SET preco = ? WHERE servico = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Configurando os preços dos serviços a partir dos JTextFields

            // Lavagem Interna
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemInterna2.getText()));
            pstmt.setString(2, "Lavagem Interna");
            pstmt.executeUpdate();

            // Lavagem Externa
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemExterna2.getText()));
            pstmt.setString(2, "Lavagem Externa");
            pstmt.executeUpdate();

            // Lavagem Completa
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemCompleta2.getText()));
            pstmt.setString(2, "Lavagem Completa");
            pstmt.executeUpdate();

            // Polimento e Enceramento
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldPolimentoEnceramento2.getText()));
            pstmt.setString(2, "Polimento e Enceramento");
            pstmt.executeUpdate();

            // Lavagem de Rodas e Pneus
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemRodasPneus2.getText()));
            pstmt.setString(2, "Lavagem de Rodas e Pneus");
            pstmt.executeUpdate();
        }

        // Confirma a transação
        conn.commit();
        JOptionPane.showMessageDialog(this, "Preços atualizados com sucesso!");

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao atualizar os preços.");
    }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testesdb", "root", "")) {
        // Inicia uma transação
        conn.setAutoCommit(false);

        // Consulta SQL para atualizar o preço do serviço
        String sql = "UPDATE preco_pesado SET preco = ? WHERE servico = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Configurando os preços dos serviços a partir dos JTextFields

            // Lavagem Interna
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemInterna3.getText()));
            pstmt.setString(2, "Lavagem Interna");
            pstmt.executeUpdate();

            // Lavagem Externa
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemExterna3.getText()));
            pstmt.setString(2, "Lavagem Externa");
            pstmt.executeUpdate();

            // Lavagem Completa
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemCompleta3.getText()));
            pstmt.setString(2, "Lavagem Completa");
            pstmt.executeUpdate();

            // Polimento e Enceramento
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldPolimentoEnceramento3.getText()));
            pstmt.setString(2, "Polimento e Enceramento");
            pstmt.executeUpdate();

            // Lavagem de Rodas e Pneus
            pstmt.setBigDecimal(1, new BigDecimal(jTextFieldLavagemRodasPneus3.getText()));
            pstmt.setString(2, "Lavagem de Rodas e Pneus");
            pstmt.executeUpdate();
        }

        // Confirma a transação
        conn.commit();
        JOptionPane.showMessageDialog(this, "Preços atualizados com sucesso!");

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao atualizar os preços.");
    }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jComboBoxTipoViaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoViaturaActionPerformed
                                                    
    // Obter o tipo de viatura selecionado
    String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();

    // Inicializar os JTextFields dos preços como vazios
    jTextFieldLavagemInterna.setText("");
    jTextFieldLavagemExterna.setText("");
    jTextFieldLavagemCompleta.setText("");
    jTextFieldPolimentoEnceramento.setText("");
    jTextFieldLavagemRodasPneus.setText("");

    // Definir a tabela de acordo com o tipo de viatura selecionado
    String tabelaPrecos;
    switch (tipoViatura) {
        case "Ligeiro":
            tabelaPrecos = "preco_ligeiro";
            break;
        case "Medio":
            tabelaPrecos = "preco_medio";
            break;
        case "Pesado":
            tabelaPrecos = "preco_pesado";
            break;
        default:
            JOptionPane.showMessageDialog(this, "Selecione um tipo de viatura válido.");
            return;
    }

    // Conectar ao banco de dados para obter os preços do tipo de viatura selecionado
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testesdb", "root", "")) {
        // Consulta SQL para obter os preços dos serviços na tabela correspondente
        String sql = "SELECT servico, preco FROM " + tabelaPrecos;
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Iterar pelos resultados e preencher os JTextFields correspondentes
            while (rs.next()) {
                String servico = rs.getString("servico");
                BigDecimal preco = rs.getBigDecimal("preco");

                // Atualizar os JTextFields com o preço correspondente
                switch (servico) {
                    case "Lavagem Interna":
                        LavagemInterna.setText(preco.toString());
                        break;
                    case "Lavagem Externa":
                       LavagemExterna.setText(preco.toString());
                        break;
                    case "Lavagem Completa":
                        LavagemCompleta.setText(preco.toString());
                        break;
                    case "Polimento e Enceramento":
                        PolimentoEnceramento.setText(preco.toString());
                        break;
                    case "Lavagem de Rodas e Pneus":
                        LavagemRodasPneus.setText(preco.toString());
                        break;
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao carregar os preços.");
    }

    }//GEN-LAST:event_jComboBoxTipoViaturaActionPerformed

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
            java.util.logging.Logger.getLogger(Home_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cadastrar;
    private javax.swing.JTextField LavagemCompleta;
    private javax.swing.JTextField LavagemExterna;
    private javax.swing.JTextField LavagemInterna;
    private javax.swing.JTextField LavagemRodasPneus;
    private javax.swing.JPanel P1;
    private javax.swing.JPanel P10;
    private javax.swing.JPanel P11;
    private javax.swing.JPanel P12;
    private javax.swing.JPanel P13;
    private javax.swing.JPanel P14;
    private javax.swing.JPanel P16;
    private javax.swing.JPanel P2;
    private javax.swing.JPanel P3;
    private javax.swing.JPanel P4;
    private javax.swing.JPanel P5;
    private javax.swing.JPanel P6;
    private javax.swing.JPanel P7;
    private javax.swing.JPanel P8;
    private javax.swing.JPanel P9;
    private javax.swing.JTabbedPane Paineis;
    private javax.swing.JTextField PolimentoEnceramento;
    private javax.swing.JLabel contacto;
    private javax.swing.JTextField contactoField;
    private javax.swing.JLabel email;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonEditar1;
    private javax.swing.JButton jButtonFeito;
    private javax.swing.JButton jButtonRemover;
    private javax.swing.JButton jButtonSalvarAgendamento;
    private javax.swing.JButton jButtonSalvarEdicao;
    private javax.swing.JButton jButtonSalvarEdicao2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JComboBox<String> jComboBoxCargo;
    private javax.swing.JComboBox<String> jComboBoxHoraEntrada;
    private javax.swing.JComboBox<String> jComboBoxHoraSaida;
    private javax.swing.JComboBox<String> jComboBoxSexo;
    private javax.swing.JComboBox<String> jComboBoxTipoMotor;
    private javax.swing.JComboBox<String> jComboBoxTipoViatura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTable jTableAgendamentos;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTable jTableFeitos;
    private javax.swing.JTable jTableFuncionarios;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextFieldContacto;
    private javax.swing.JTextField jTextFieldContacto2;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldHoraEntrada;
    private javax.swing.JTextField jTextFieldHoraSaida;
    private javax.swing.JTextField jTextFieldLavagemCompleta;
    private javax.swing.JTextField jTextFieldLavagemCompleta2;
    private javax.swing.JTextField jTextFieldLavagemCompleta3;
    private javax.swing.JTextField jTextFieldLavagemExterna;
    private javax.swing.JTextField jTextFieldLavagemExterna2;
    private javax.swing.JTextField jTextFieldLavagemExterna3;
    private javax.swing.JTextField jTextFieldLavagemInterna;
    private javax.swing.JTextField jTextFieldLavagemInterna2;
    private javax.swing.JTextField jTextFieldLavagemInterna3;
    private javax.swing.JTextField jTextFieldLavagemRodasPneus;
    private javax.swing.JTextField jTextFieldLavagemRodasPneus2;
    private javax.swing.JTextField jTextFieldLavagemRodasPneus3;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldMatricula;
    private javax.swing.JTextField jTextFieldMorada;
    private javax.swing.JTextField jTextFieldNomeCompleto;
    private javax.swing.JTextField jTextFieldPlaca;
    private javax.swing.JTextField jTextFieldPolimentoEnceramento;
    private javax.swing.JTextField jTextFieldPolimentoEnceramento2;
    private javax.swing.JTextField jTextFieldPolimentoEnceramento3;
    private javax.swing.JTextField jTextFieldProprietario;
    private javax.swing.JTextField jTextFieldSalario;
    private javax.swing.JTextField jTextFieldSenha;
    private javax.swing.JLabel jTextFieldTotal;
    private javax.swing.JLabel marca;
    private javax.swing.JTextField marcaField;
    private javax.swing.JLabel matricula;
    private javax.swing.JLabel modelo;
    private javax.swing.JTextField modeloField;
    private javax.swing.JLabel morada;
    private javax.swing.JTextField moradaField;
    private javax.swing.JLabel nome;
    private javax.swing.JTextField nomeField;
    private javax.swing.JTextField pesquisajTextField;
    private javax.swing.JLabel sexo;
    private javax.swing.JComboBox<String> sexoComboBox;
    private javax.swing.JComboBox<String> statusComboBox;
    // End of variables declaration//GEN-END:variables
}
