INSERT INTO categories(id, user_id, name, color, icon, system_default)
VALUES (gen_random_uuid(), NULL, 'Food', '#f97316', 'utensils', true),
       (gen_random_uuid(), NULL, 'Transport', '#06b6d4', 'car', true),
       (gen_random_uuid(), NULL, 'Rent', '#8b5cf6', 'home', true),
       (gen_random_uuid(), NULL, 'Utilities', '#eab308', 'bolt', true),
       (gen_random_uuid(), NULL, 'Software', '#22c55e', 'code', true),
       (gen_random_uuid(), NULL, 'Tax', '#ef4444', 'receipt', true),
       (gen_random_uuid(), NULL, 'Income', '#10b981', 'wallet', true),
       (gen_random_uuid(), NULL, 'Other', '#64748b', 'circle', true);
