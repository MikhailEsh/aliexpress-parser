create or replace table if not exists links_group
(
	url_group text not null,
	id int auto_increment,
	constraint links_group_id_uindex
		unique (id)
);

alter table links_group
	add primary key (id);


create or replace table if not exists links_products
(
	url_group text not null,
	url_product text not null,
	id int auto_increment,
	constraint links_products_id_uindex
		unique (id)
);

alter table links_products
	add primary key (id);



