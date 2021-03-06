create or replace table if not exists errors
(
	url_product text not null,
	id int auto_increment,
	url_group int default 1 not null,
	constraint errors_id_uindex
		unique (id)
);

alter table errors
	add primary key (id);

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
	url_product text not null,
	id varchar(255) not null,
	url_feedback text not null,
	count_feedback int not null,
	count_ru_feedback int not null,
	ratio int not null,
	url_group int not null,
	constraint links_products_id_uindex
		unique (id),
	constraint links_products_links_group_id_fk
		foreign key (url_group) references links_group (id)
);

alter table links_products
	add primary key (id);

