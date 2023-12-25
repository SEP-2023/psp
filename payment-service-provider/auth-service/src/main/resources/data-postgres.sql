INSERT INTO public.role (id, name) VALUES (1,'AGENCY_ADMIN');
INSERT INTO permission(id, name) VALUES (1, 'UPDATE_PAYMENT_METHODS_PERMISSION');
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 1);