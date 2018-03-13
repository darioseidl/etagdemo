INSERT INTO `parent` (`id`, `version`, `name`) VALUES (1, 0, 'hans');

INSERT INTO `child` (`id`, `version`, `name`, `parent_id`) VALUES (1, 0, 'haenschen', 1);