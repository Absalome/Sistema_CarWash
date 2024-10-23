
package View;

import javax.swing.JOptionPane;
import connect.Conexao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
/**
 *
 * @author Absalome
 */
public class Home_Cliente extends javax.swing.JFrame {

    /**
     * Creates new form Home_Cliente
     */
    public Home_Cliente() {
        initComponents();
        
        
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        Paineis = new javax.swing.JTabbedPane();
        P3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        P5 = new javax.swing.JPanel();
        P6 = new javax.swing.JPanel();
        P1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
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
        P4 = new javax.swing.JPanel();
        P8 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        P9 = new javax.swing.JPanel();
        P10 = new javax.swing.JPanel();
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
        jTextFieldContacto2 = new javax.swing.JTextField();
        jComboBoxTipoMotor = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        jButton1.setText("Faer Cadastro");
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
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 188, 37));

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/home-40.png"))); // NOI18N
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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 70, 39));

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-reports-32.png"))); // NOI18N
        jButton4.setText("FeedBack");
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
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 540, 186, 35));

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
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 607, -1, -1));

        jButton7.setBackground(new java.awt.Color(204, 204, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 0, 0));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-services-32.png"))); // NOI18N
        jButton7.setText("       Situação");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 430, 181, 38));

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
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 380, 185, 32));

        jButton9.setBackground(new java.awt.Color(204, 204, 204));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 0, 0));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-warehouse-32.png"))); // NOI18N
        jButton9.setText("      Promoções");
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
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 486, 186, 36));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, -1));

        P3.setBackground(new java.awt.Color(0, 204, 204));
        P3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("BEM-VINDO CLIENTE");
        P3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, -10, 270, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/pngwing.com 2.png"))); // NOI18N
        P3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 940, 690));

        Paineis.addTab("tab1", P3);

        P5.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout P5Layout = new javax.swing.GroupLayout(P5);
        P5.setLayout(P5Layout);
        P5Layout.setHorizontalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1068, Short.MAX_VALUE)
        );
        P5Layout.setVerticalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );

        Paineis.addTab("tab2", P5);

        P6.setBackground(new java.awt.Color(51, 0, 51));

        javax.swing.GroupLayout P6Layout = new javax.swing.GroupLayout(P6);
        P6.setLayout(P6Layout);
        P6Layout.setHorizontalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1068, Short.MAX_VALUE)
        );
        P6Layout.setVerticalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
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
                .addContainerGap(582, Short.MAX_VALUE))
        );
        P1Layout.setVerticalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P1Layout.createSequentialGroup()
                .addContainerGap(361, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(302, 302, 302))
        );

        Paineis.addTab("tab5", P1);

        P2.setBackground(new java.awt.Color(0, 204, 204));
        P2.setForeground(new java.awt.Color(0, 0, 0));
        P2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("FAZER CADASTRO");
        P2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 6, -1, 38));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Sexo");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 141, -1, -1));

        jTextFieldNomeCompleto.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldNomeCompleto.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldNomeCompleto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jTextFieldNomeCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldNomeCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeCompletoActionPerformed(evt);
            }
        });
        P2.add(jTextFieldNomeCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 92, 164, 24));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Email");
        P2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 181, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Cargo");
        P2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 220, -1, -1));

        jTextFieldEmail.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldEmail.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldEmail.setBorder(null);
        P2.add(jTextFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 178, 164, 22));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Contacto");
        P2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 296, -1, -1));

        jTextFieldContacto.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldContacto.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldContacto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        P2.add(jTextFieldContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 293, 164, 22));

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
        P2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 345, -1, -1));

        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(0, 0, 0));
        jButton13.setText("Detalhes do Funcionario");
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
        P2.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 349, -1, -1));

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
        P2.add(jButtonEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(191, 345, -1, -1));

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
        P2.add(jButtonSalvarEdicao, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 346, -1, -1));

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

        P2.add(P11, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, -1, 177));

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

        P2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 388, 946, 291));

        jComboBoxCargo.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxCargo.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supervisor", "Operador de Máquina", "Auxiliar de Limpeza", "Atendente", "Recepcionista", "Lavador de Carros", "Técnico de Manutenção", " ", " " }));
        jComboBoxCargo.setBorder(null);
        P2.add(jComboBoxCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 218, 164, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Morada");
        P2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 258, -1, -1));

        jTextFieldMorada.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMorada.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldMorada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jTextFieldMorada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMoradaActionPerformed(evt);
            }
        });
        P2.add(jTextFieldMorada, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 256, 164, -1));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Nome");
        P2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 96, -1, -1));

        jComboBoxSexo.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSexo.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        jComboBoxSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSexoActionPerformed(evt);
            }
        });
        P2.add(jComboBoxSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 138, 164, -1));

        Paineis.addTab("tab6", P2);

        P4.setBackground(new java.awt.Color(255, 0, 204));

        javax.swing.GroupLayout P4Layout = new javax.swing.GroupLayout(P4);
        P4.setLayout(P4Layout);
        P4Layout.setHorizontalGroup(
            P4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1068, Short.MAX_VALUE)
        );
        P4Layout.setVerticalGroup(
            P4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );

        Paineis.addTab("tab7", P4);

        P8.setBackground(new java.awt.Color(0, 153, 153));

        jButton3.setText("jButton3");

        javax.swing.GroupLayout P8Layout = new javax.swing.GroupLayout(P8);
        P8.setLayout(P8Layout);
        P8Layout.setHorizontalGroup(
            P8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P8Layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addComponent(jButton3)
                .addContainerGap(589, Short.MAX_VALUE))
        );
        P8Layout.setVerticalGroup(
            P8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P8Layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jButton3)
                .addContainerGap(387, Short.MAX_VALUE))
        );

        Paineis.addTab("tab8", P8);

        P9.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout P9Layout = new javax.swing.GroupLayout(P9);
        P9.setLayout(P9Layout);
        P9Layout.setHorizontalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1068, Short.MAX_VALUE)
        );
        P9Layout.setVerticalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );

        Paineis.addTab("tab9", P9);

        P10.setBackground(new java.awt.Color(18, 181, 185));

        javax.swing.GroupLayout P10Layout = new javax.swing.GroupLayout(P10);
        P10.setLayout(P10Layout);
        P10Layout.setHorizontalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1068, Short.MAX_VALUE)
        );
        P10Layout.setVerticalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
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
        P7.add(jTextFieldPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 138, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("AGENDAMENTOS");
        P7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

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
        jComboBoxTipoViatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "             ", "Pesado", "Especial", "Ligeiro Pessoal", "Motocicleta" }));
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

        Paineis.addTab("tab4", P7);

        getContentPane().add(Paineis, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, -30, -1, 710));

        setSize(new java.awt.Dimension(1367, 763));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P2);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P3);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void jTextFieldNomeCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeCompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeCompletoActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        // TODO add your handling code here:
        P11.setVisible(true);
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        P11.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonSalvarEdicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarEdicaoActionPerformed

    }//GEN-LAST:event_jButtonSalvarEdicaoActionPerformed

    private void jTextFieldMoradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMoradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMoradaActionPerformed

    private void jComboBoxSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSexoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        Paineis.setSelectedComponent(P5);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P9);
    }//GEN-LAST:event_jButton9MouseClicked

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
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_Cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel P1;
    private javax.swing.JPanel P10;
    private javax.swing.JPanel P11;
    private javax.swing.JPanel P2;
    private javax.swing.JPanel P3;
    private javax.swing.JPanel P4;
    private javax.swing.JPanel P5;
    private javax.swing.JPanel P6;
    private javax.swing.JPanel P7;
    private javax.swing.JPanel P8;
    private javax.swing.JPanel P9;
    private javax.swing.JTabbedPane Paineis;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonSalvarAgendamento;
    private javax.swing.JButton jButtonSalvarEdicao;
    private javax.swing.JCheckBox jCheckBoxHibernizacao;
    private javax.swing.JCheckBox jCheckBoxLavagemCompleta;
    private javax.swing.JCheckBox jCheckBoxLavagemExterna;
    private javax.swing.JCheckBox jCheckBoxLavagemInterna;
    private javax.swing.JCheckBox jCheckBoxLavagemRodape;
    private javax.swing.JCheckBox jCheckBoxPolimento;
    private javax.swing.JComboBox<String> jComboBoxCargo;
    private javax.swing.JComboBox<String> jComboBoxHoraEntrada;
    private javax.swing.JComboBox<String> jComboBoxHoraSaida;
    private javax.swing.JComboBox<String> jComboBoxSexo;
    private javax.swing.JComboBox<String> jComboBoxTipoMotor;
    private javax.swing.JComboBox<String> jComboBoxTipoViatura;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFuncionarios;
    private javax.swing.JTextField jTextFieldContacto;
    private javax.swing.JTextField jTextFieldContacto2;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldHoraEntrada;
    private javax.swing.JTextField jTextFieldHoraSaida;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldMorada;
    private javax.swing.JTextField jTextFieldNomeCompleto;
    private javax.swing.JTextField jTextFieldPlaca;
    private javax.swing.JTextField jTextFieldProprietario;
    private javax.swing.JTextField jTextFieldSalario;
    private javax.swing.JLabel jTextFieldTotal;
    // End of variables declaration//GEN-END:variables

    public static class setLocationRelativeTo {

        public setLocationRelativeTo(Object object) {
        }
    }
}
