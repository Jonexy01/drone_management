INSERT INTO drone (serial_number, model, weight_limit, battery_capacity, state) VALUES
('DRN-001', 'Lightweight', 200, 100, 'IDLE'),
('DRN-002', 'Lightweight', 250, 85, 'IDLE'),
('DRN-003', 'Middleweight', 350, 10, 'IDLE'),
('DRN-004', 'Middleweight', 400, 50, 'LOADING'),
('DRN-005', 'Cruiserweight', 450, 75, 'LOADED'),
('DRN-006', 'Cruiserweight', 450, 90, 'DELIVERING'),
('DRN-007', 'Heavyweight', 500, 100, 'IDLE'),
('DRN-008', 'Heavyweight', 500, 20, 'IDLE'),
('DRN-009', 'Heavyweight', 500, 60, 'RETURNING'),
('DRN-010', 'Lightweight', 150, 95, 'IDLE');


INSERT INTO medication (name, weight, code, image) VALUES
('Antibiotic-A1', 50, 'ANT_001', 'image_data_placeholder_1'),
('Painkiller_B2', 100, 'PK_99', 'image_data_placeholder_2'),
('Insulin-Z', 30, 'INS_555', 'image_data_placeholder_3');