create PROCEDURE loop_insert(in cnt INT)
BEGIN 
	declare i int default 1;
	while (i <= cnt) do 
		insert into board (title, userid, contents)
		values ('aaaa', '11', '가나다라마');
		insert into board (title, userid, contents)
		values ('bbbb', '22', '아아앙');
		insert into board (title, userid, contents)
		values ('cccc', '33', '카카카카카');
		set i = i + 1;
	end while;
end;