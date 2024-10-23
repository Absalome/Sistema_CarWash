
package View;

import connect.Conexao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



/**
 *
 * @author Absalome
 */
public class Home_Funcionario extends javax.swing.JFrame {

    /**
     * Creates new form Home_Funcionario
     */
    public Home_Funcionario() {
        initComponents();
        carregarClientesNaTabela();
        carregarAgendamentos();
        carregarAgendamentosFeitos();
        
        
         jCheckBoxLavagemInterna.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxLavagemExterna.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxPolimento.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxLavagemCompleta.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxLavagemRodape.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxHibernizacao.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

         

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


    // Verificando os checkboxes e adicionando os preços dos serviços
    if (jCheckBoxLavagemInterna.isSelected()) {
        total += 1000; 
    }
    if (jCheckBoxLavagemExterna.isSelected()) {
        total += 800; 
    }
    if (jCheckBoxPolimento.isSelected()) {
        total += 10000; 
    }
    if (jCheckBoxLavagemCompleta.isSelected()) {
        total += 1700; 
    }
    if (jCheckBoxLavagemRodape.isSelected()) {
        total += 500; 
    }
    if (jCheckBoxHibernizacao.isSelected()) {
        total += 1500; 
    }

    // Atualizando o campo de total com o resultado final
    jTextFieldTotal.setText(String.valueOf(total));
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
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        Paineis = new javax.swing.JTabbedPane();
        P3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        P5 = new javax.swing.JPanel();
        P6 = new javax.swing.JPanel();
        P1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        P9 = new javax.swing.JPanel();
        P2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sexo = new javax.swing.JLabel();
        nomeField = new javax.swing.JTextField();
        email = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        contacto = new javax.swing.JLabel();
        moradaField = new javax.swing.JTextField();
        Cadastrar = new javax.swing.JButton();
        P11 = new javax.swing.JPanel();
        marca = new javax.swing.JLabel();
        modelo = new javax.swing.JLabel();
        matricula = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        marcaField = new javax.swing.JTextField();
        modeloField = new javax.swing.JTextField();
        jTextFieldMatricula = new javax.swing.JTextField();
        morada = new javax.swing.JLabel();
        contactoField = new javax.swing.JTextField();
        nome = new javax.swing.JLabel();
        sexoComboBox = new javax.swing.JComboBox<>();
        P10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jButtonEditar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        pesquisajTextField = new javax.swing.JTextField();
        P7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jCheckBoxLavagemInterna = new javax.swing.JCheckBox();
        jCheckBoxLavagemExterna = new javax.swing.JCheckBox();
        jCheckBoxPolimento = new javax.swing.JCheckBox();
        jCheckBoxLavagemCompleta = new javax.swing.JCheckBox();
        jCheckBoxHibernizacao = new javax.swing.JCheckBox();
        jTextFieldMarca = new javax.swing.JTextField();
        jTextFieldPlaca = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButtonSalvarAgendamento = new javax.swing.JButton();
        jTextFieldTotal = new javax.swing.JLabel();
        jCheckBoxLavagemRodape = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldProprietario = new javax.swing.JTextField();
        jComboBoxTipoViatura = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldHoraEntrada = new javax.swing.JTextField();
        jTextFieldHoraSaida = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldContacto = new javax.swing.JTextField();
        jComboBoxTipoMotor = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        P8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAgendamentos = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jButtonSalvarEdicao2 = new javax.swing.JButton();
        jButtonRemover = new javax.swing.JButton();
        jButtonFeito = new javax.swing.JButton();
        P12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableFeitos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1400, 750));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/ABSA_Car_wash-removebg-preview (1).png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 206, 153));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-add-administrator-32.png"))); // NOI18N
        jButton1.setText("Cadastrar Cliente");
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
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 220, 37));

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("HOME");
        jButton2.setBorder(null);
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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 177, 40, 30));

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-eye-32.png"))); // NOI18N
        jButton3.setText("Ver  Dados Clientes");
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
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 325, 220, 37));

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
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 220, 35));

        jButton6.setBackground(new java.awt.Color(204, 204, 204));
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_cancel_30px.png"))); // NOI18N
        jButton6.setText("Sair");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton6MouseExited(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 660, -1, -1));

        jButton7.setBackground(new java.awt.Color(204, 204, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 0, 0));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-services-32.png"))); // NOI18N
        jButton7.setText("Agendamentos Pendentes");
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
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 430, 220, 38));

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
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 380, 220, 32));

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
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 220, 36));

        jButton10.setText("Agendamentos Feitos");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 220, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 730));

        P3.setBackground(new java.awt.Color(0, 204, 204));
        P3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("BEM-VINDO FUNCIONÁRIO");
        P3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 330, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/pngwing.com 2.png"))); // NOI18N
        P3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 1110, 690));

        Paineis.addTab("tab1", P3);

        P5.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout P5Layout = new javax.swing.GroupLayout(P5);
        P5.setLayout(P5Layout);
        P5Layout.setHorizontalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3470, Short.MAX_VALUE)
        );
        P5Layout.setVerticalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        Paineis.addTab("tab2", P5);

        P6.setBackground(new java.awt.Color(51, 0, 51));

        javax.swing.GroupLayout P6Layout = new javax.swing.GroupLayout(P6);
        P6.setLayout(P6Layout);
        P6Layout.setHorizontalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3470, Short.MAX_VALUE)
        );
        P6Layout.setVerticalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        Paineis.addTab("tab3", P6);

        P1.setBackground(new java.awt.Color(102, 255, 102));

        jLabel12.setText("jLabel12");

        javax.swing.GroupLayout P1Layout = new javax.swing.GroupLayout(P1);
        P1.setLayout(P1Layout);
        P1Layout.setHorizontalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P1Layout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(jLabel12)
                .addContainerGap(2984, Short.MAX_VALUE))
        );
        P1Layout.setVerticalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P1Layout.createSequentialGroup()
                .addContainerGap(411, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(302, 302, 302))
        );

        Paineis.addTab("tab5", P1);

        P9.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout P9Layout = new javax.swing.GroupLayout(P9);
        P9.setLayout(P9Layout);
        P9Layout.setHorizontalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3470, Short.MAX_VALUE)
        );
        P9Layout.setVerticalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        Paineis.addTab("tab9", P9);

        P2.setBackground(new java.awt.Color(0, 204, 204));
        P2.setForeground(new java.awt.Color(0, 0, 0));
        P2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("CADASTRO DE CLIENTES");
        P2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 6, -1, 38));

        sexo.setBackground(new java.awt.Color(0, 0, 0));
        sexo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sexo.setForeground(new java.awt.Color(0, 0, 0));
        sexo.setText("Sexo");
        sexo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P2.add(sexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, -1, -1));

        nomeField.setBackground(new java.awt.Color(255, 255, 255));
        nomeField.setForeground(new java.awt.Color(0, 0, 0));
        nomeField.setBorder(null);
        nomeField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        P2.add(nomeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 170, 24));

        email.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        email.setForeground(new java.awt.Color(0, 0, 0));
        email.setText("Email");
        P2.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        emailField.setBackground(new java.awt.Color(255, 255, 255));
        emailField.setForeground(new java.awt.Color(0, 0, 0));
        emailField.setBorder(null);
        emailField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailFieldKeyTyped(evt);
            }
        });
        P2.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 170, 22));

        contacto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        contacto.setForeground(new java.awt.Color(0, 0, 0));
        contacto.setText("Contacto");
        P2.add(contacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        moradaField.setBackground(new java.awt.Color(255, 255, 255));
        moradaField.setForeground(new java.awt.Color(0, 0, 0));
        moradaField.setBorder(null);
        P2.add(moradaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 170, 22));

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
        P2.add(Cadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, -1, -1));

        P11.setBackground(new java.awt.Color(0, 153, 153));

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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Status");

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

        javax.swing.GroupLayout P11Layout = new javax.swing.GroupLayout(P11);
        P11.setLayout(P11Layout);
        P11Layout.setHorizontalGroup(
            P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P11Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marca)
                    .addComponent(matricula)
                    .addComponent(jLabel9))
                .addGap(37, 37, 37)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(marcaField)
                    .addComponent(statusComboBox, 0, 171, Short.MAX_VALUE)
                    .addComponent(modeloField)
                    .addComponent(jTextFieldMatricula))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        P11Layout.setVerticalGroup(
            P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P11Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marca)
                    .addComponent(marcaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modelo)
                    .addComponent(modeloField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matricula)
                    .addComponent(jTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        P2.add(P11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 390, 290));

        morada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        morada.setForeground(new java.awt.Color(0, 0, 0));
        morada.setText("Morada");
        P2.add(morada, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, -1, -1));

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
        P2.add(contactoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 170, 20));

        nome.setBackground(new java.awt.Color(0, 0, 0));
        nome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nome.setForeground(new java.awt.Color(0, 0, 0));
        nome.setText("Nome");
        P2.add(nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        sexoComboBox.setBackground(new java.awt.Color(255, 255, 255));
        sexoComboBox.setForeground(new java.awt.Color(0, 0, 0));
        sexoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        sexoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sexoComboBoxActionPerformed(evt);
            }
        });
        P2.add(sexoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 170, -1));

        Paineis.addTab("tab6", P2);

        P10.setBackground(new java.awt.Color(18, 181, 185));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Clientes Cadastrados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(jLabel3)
                .addContainerGap(599, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
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
        jScrollPane2.setViewportView(jTableClientes);

        jButtonEditar.setBackground(new java.awt.Color(51, 255, 51));
        jButtonEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonEditar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEditar.setText("Editar");
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 0, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setText("Excluir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Pesquisar:");

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
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(jButtonEditar)
                        .addGap(93, 93, 93)
                        .addComponent(jButton5))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1057, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2234, Short.MAX_VALUE))
        );
        P10Layout.setVerticalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pesquisajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEditar)
                    .addComponent(jButton5))
                .addGap(77, 77, 77))
        );

        Paineis.addTab("tab10", P10);

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

        jCheckBoxLavagemInterna.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemInterna.setText("Lavagem Interna");
        P7.add(jCheckBoxLavagemInterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

        jCheckBoxLavagemExterna.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemExterna.setText("Lavagem Externa");
        P7.add(jCheckBoxLavagemExterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, -1, -1));

        jCheckBoxPolimento.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxPolimento.setText("Polimento e Enceramento");
        P7.add(jCheckBoxPolimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 355, -1, -1));

        jCheckBoxLavagemCompleta.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemCompleta.setText("Lavagem Completa");
        P7.add(jCheckBoxLavagemCompleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, -1, -1));

        jCheckBoxHibernizacao.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxHibernizacao.setText("Hibernização e Tratamento de Bancos");
        P7.add(jCheckBoxHibernizacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        jTextFieldMarca.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 138, -1));

        jTextFieldPlaca.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPlacaActionPerformed(evt);
            }
        });
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

        jCheckBoxLavagemRodape.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemRodape.setText("Lavagem Externa");
        P7.add(jCheckBoxLavagemRodape, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 400, -1, -1));

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
        jComboBoxTipoViatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pesado", "Especial", "Ligeiro Pessoal", "Motocicleta" }));
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

        jTextFieldContacto.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldContacto.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 120, -1));

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

        Paineis.addTab("tab4", P7);

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

        P8.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1090, -1));

        jButton11.setText("Editar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        P8.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 580, -1, -1));

        jButtonSalvarEdicao2.setText("Salvar");
        jButtonSalvarEdicao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarEdicao2ActionPerformed(evt);
            }
        });
        P8.add(jButtonSalvarEdicao2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 580, -1, -1));

        jButtonRemover.setText("Remover");
        jButtonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });
        P8.add(jButtonRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 580, -1, -1));

        jButtonFeito.setText("Feito");
        jButtonFeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFeitoActionPerformed(evt);
            }
        });
        P8.add(jButtonFeito, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 60, -1));

        Paineis.addTab("tab8", P8);

        P12.setBackground(new java.awt.Color(51, 51, 51));
        P12.setToolTipText("");
        P12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableFeitos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "marca", "placa", "proprietario", "contacto", "tipo_viatura", "hora_entrada", "hora_saida", "total_servicos"
            }
        ));
        jScrollPane3.setViewportView(jTableFeitos);

        P12.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 151, 1056, -1));

        Paineis.addTab("tab5", P12);

        getContentPane().add(Paineis, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, -30, 3470, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P2);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P3);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P10);

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        Paineis.setSelectedComponent(P5);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        Paineis.setSelectedComponent(P1);
        dispose();
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseExited
        // TODO add your handling code here
        this.dispose();
    }//GEN-LAST:event_jButton6MouseExited

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P8);
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P7);
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P9);
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

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

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        int selectedRow = jTableClientes.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = jTableClientes.convertRowIndexToModel(selectedRow);


        String nome = (String) jTableClientes.getValueAt(modelRow, 1); 
        String sexo = (String) jTableClientes.getValueAt(modelRow, 2); 
        String email = (String) jTableClientes.getValueAt(modelRow, 3);
        String contacto = (String) jTableClientes.getValueAt(modelRow, 4);
        String morada = (String) jTableClientes.getValueAt(modelRow, 5);
        String marca = (String) jTableClientes.getValueAt(modelRow, 6);
        String modelo = (String) jTableClientes.getValueAt(modelRow, 7);
        String matricula = (String) jTableClientes.getValueAt(modelRow, 8);
        String status = (String) jTableClientes.getValueAt(modelRow, 9);

        // Configura os dados no painel de cadastro
        setClienteData(nome, sexo, email, contacto, morada, marca, modelo, matricula, status);

       
        Paineis.setSelectedComponent(P2);
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

    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
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

    }//GEN-LAST:event_jButton5ActionPerformed

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

    private void jTextFieldPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPlacaActionPerformed
        // Chamar o método para buscar os dados do cliente com base na matrícula
        buscarDadosClientePorMatricula();
        }

        // Método para buscar os dados do cliente com base na matrícula
        private void buscarDadosClientePorMatricula() {
            // Obter a matrícula inserida no JTextField
            String matricula = jTextFieldPlaca.getText().trim();

            // Verificar se a matrícula foi informada
            if (matricula.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, insira a matrícula para buscar os dados do cliente.", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Connection conexao = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultado = null;

            try {
                // Configurar a conexão com o banco de dados
                String url = "jdbc:mysql://localhost:3306/testesdb";
                String usuario = "root";
                String senha = "";

                conexao = DriverManager.getConnection(url, usuario, senha);

                // SQL para buscar os dados do cliente com base na matrícula
                String sql = "SELECT nome, marca, contacto FROM clientes WHERE matricula = ?";
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, matricula);

                // Executar a consulta
                resultado = preparedStatement.executeQuery();

                // Verificar se o cliente foi encontrado
                if (resultado.next()) {
                    // Obter os dados do cliente
                    String nome = resultado.getString("nome");
                    String marca = resultado.getString("marca");
                    String contacto = resultado.getString("contacto");

                    // Preencher os campos na tela de agendamento (P2)
                    jTextFieldProprietario.setText(nome);
                    jTextFieldMarca.setText(marca);
                    jTextFieldContacto.setText(contacto);
                } else {
                    // Exibir mensagem caso a matrícula não seja encontrada
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado para a matrícula fornecida.", "Cliente não encontrado", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                // Fechar os recursos
                try {
                    if (resultado != null) resultado.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (conexao != null) conexao.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar os recursos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

    }//GEN-LAST:event_jTextFieldPlacaActionPerformed

    private void jButtonSalvarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAgendamentoActionPerformed
        // TODO add your handling code here:

        // Obtenha os valores dos campos
        String marca = jTextFieldMarca.getText();
        String placa = jTextFieldPlaca.getText();
        String proprietario = jTextFieldProprietario.getText();
        String contacto = jTextFieldContacto.getText();
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

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
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
    }//GEN-LAST:event_jButton11ActionPerformed

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
        String contacto = jTextFieldContacto.getText();
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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        carregarAgendamentos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Paineis.setSelectedComponent(P12);
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_Funcionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cadastrar;
    private javax.swing.JPanel P1;
    private javax.swing.JPanel P10;
    private javax.swing.JPanel P11;
    private javax.swing.JPanel P12;
    private javax.swing.JPanel P2;
    private javax.swing.JPanel P3;
    private javax.swing.JPanel P5;
    private javax.swing.JPanel P6;
    private javax.swing.JPanel P7;
    private javax.swing.JPanel P8;
    private javax.swing.JPanel P9;
    private javax.swing.JTabbedPane Paineis;
    private javax.swing.JLabel contacto;
    private javax.swing.JTextField contactoField;
    private javax.swing.JLabel email;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonFeito;
    private javax.swing.JButton jButtonRemover;
    private javax.swing.JButton jButtonSalvarAgendamento;
    private javax.swing.JButton jButtonSalvarEdicao2;
    private javax.swing.JCheckBox jCheckBoxHibernizacao;
    private javax.swing.JCheckBox jCheckBoxLavagemCompleta;
    private javax.swing.JCheckBox jCheckBoxLavagemExterna;
    private javax.swing.JCheckBox jCheckBoxLavagemInterna;
    private javax.swing.JCheckBox jCheckBoxLavagemRodape;
    private javax.swing.JCheckBox jCheckBoxPolimento;
    private javax.swing.JComboBox<String> jComboBoxTipoMotor;
    private javax.swing.JComboBox<String> jComboBoxTipoViatura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableAgendamentos;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTable jTableFeitos;
    private javax.swing.JTextField jTextFieldContacto;
    private javax.swing.JTextField jTextFieldHoraEntrada;
    private javax.swing.JTextField jTextFieldHoraSaida;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldMatricula;
    private javax.swing.JTextField jTextFieldPlaca;
    private javax.swing.JTextField jTextFieldProprietario;
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

    public static class setLocationRelativeTo {

        public setLocationRelativeTo(Object object) {
        }
    }
}
