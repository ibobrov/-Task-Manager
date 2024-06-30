insert into backend.users(email, password, full_name, confirmed_registration, active, role)
values ('admin@admin.ru', '$2a$10$HdINihg6cUWqHAnEOIsgBu91luwLPTHXrgpKJAejHVh..Mg39ta7K', 'administrator', true, true, 'ADMIN')
on conflict do nothing;