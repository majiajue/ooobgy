SQLite format 3   @                                                                             �    � � �                                                                                                                              �)�itriggershoe_color_updshoe CREATE TRIGGER shoe_color_upd
 BEFORE UPDATE ON shoe
  FOR EACH ROW BEGIN
      SELECT RAISE(ROLLBACK, 'update on table "shoe" violates foreign key constraint "cid"')
      WHERE  (SELECT cid FROM color WHERE cid = NEW.cid) IS NULL;
  END�)�itriggershoe_color_insshoe CREATE TRIGGER shoe_color_ins
 BEFORE INSERT ON shoe
  FOR EACH ROW BEGIN
      SELECT RAISE(ROLLBACK, 'insert on table "shoe" violates foreign key constraint "cid"')
      WHERE  (SELECT cid FROM color WHERE cid = NEW.cid) IS NULL;
  END��etableshoeshoeCREATE TABLE shoe (sid integer primary key not null, path text not null , cid integer references color(cid))a�!tablecolorcolorCREATE TABLE color (cid integer primary key not null, color text not       � ��������                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          willdel	 purple white black	 yellow blue green red   � ������                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     3!willdel003   "!willdel002   !willdel001 red003 red002 red001   � ��������                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              D!willdel004   3!willdel003   "!willdel002   !willdel001 white001 blue003 blue002 blue001    �  �X��{                                                                                              a�!tablecolorcolorCREATE TABLE color (cid integer primary key not null, color text not null)��etableshoeshoeCREATE TABa�!tablecolorcolorCREATE TABLE color (cid integer primary key not null, color text not null)��etableshoeshoeCREATE TABLE shoe (sid integer primary key not null, path text not null , cid integer references color(cid))�)�itriggershoe_color_insshoe CREATE TRIGGER shoe_color_ins
 BEFORE INSERT ON shoe
  FOR EACH ROW BEGIN
      SELECT RAISE(ROLLBACK, 'insert on table "shoe" violates foreign key constraint "cid"')
      WHERE  (SELECT cid FROM color WHERE cid = NEW.cid) IS NULL;
  END�)�itriggershoe_color_updshoe CREATE TRIGGER shoe_color_upd
 BEFORE UPDATE ON shoe
  FOR EACH ROW BEGIN
      SELECT RAISE(ROLLBACK, 'update on table "shoe" violates foreign key constraint "cid"')
      WHERE  (SELECT cid FROM color WHERE cid = NEW.cid) IS NULL;
  END    � i�W �                                                                                                                                    �@�Striggercolor_delcolor CREATE TRIGGER color_del
 BEFORE DELETE ON color
  FOR EACH ROW BEGIN
      delete from shoe where cid = OLD.cid;
      delete from sock where cid = OLD.cid;
  END�)�itriggersock_color_updsock CREATE TRIGGER sock_color_upd
 BEFORE UPDATE ON sock
  FOR EACH ROW BEGIN
      SELECT RAISE(ROLLBACK, 'update on table "sock" violates foreign key constraint "cid"')
      WHERE  (SELECT cid FROM color WHERE cid = NEW.cid) IS NULL;
  END��gtablesocksockCREATE TABLE sock (soid integer primary key not null, path text not null , cid integer references color(cid))�)�itriggersock_color_inssock CREATE TRIGGER sock_color_ins
 BEFORE INSERT ON sock
  FOR EACH ROW BEGIN
      SELECT RAISE(ROLLBACK, 'insert on table "sock" violates foreign key constraint "cid"')
      WHERE  (SELECT cid FROM color WHERE cid = NEW.cid) IS NULL;
  END