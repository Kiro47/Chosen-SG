package com.kiro.sg.game.lobby.voting;

import com.kiro.sg.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingMapRenderer extends MapRenderer
{
	private static final int MAP_SIZE = 128;
	private static final int FONT_SIZE = 20;
	private static final List<MapView> maps = new ArrayList<>();
	private final Map<Short, Image> images;
	private final Map<Short, Boolean> needsChange;

	public VotingMapRenderer()
	{
		super(false);
		images = new HashMap<>();
		needsChange = new HashMap<>();
	}

	public static void sendToPlayer(Player player)
	{
		for (MapView view : maps)
		{
			player.sendMap(view);
		}
	}

	@Override
	public void initialize(MapView view)
	{
		super.initialize(view);
		maps.add(view);
	}

	public void applyTo(MapView mapView)
	{
		for (MapRenderer renderer : mapView.getRenderers())
		{
			mapView.removeRenderer(renderer);
		}
		mapView.addRenderer(this);
	}

	private static Image getScaledImage(Image image, int quadrant)
	{
		BufferedImage img = new BufferedImage(MAP_SIZE, MAP_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = img.getGraphics();
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		switch (quadrant)
		{
			case 0:
				graphics.drawImage(image, 0, 0, MAP_SIZE, MAP_SIZE, width >> 1, 0, width, height >> 1, null);
				break;
			case 1:
				graphics.drawImage(image, 0, 0, MAP_SIZE, MAP_SIZE, 0, 0, width >> 1, height >> 1, null);
				break;
			case 2:
				graphics.drawImage(image, 0, 0, MAP_SIZE, MAP_SIZE, 0, height >> 1, width >> 1, height, null);
				break;
			case 3:
				graphics.drawImage(image, 0, 0, MAP_SIZE, MAP_SIZE, width >> 1, height >> 1, width, height, null);
				break;
			default:
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, MAP_SIZE, MAP_SIZE);
				graphics.setColor(Color.BLACK);
				graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
				graphics.drawString("Error", 0, MAP_SIZE >> 1);
		}

		return img;
	}

	public void loadImage(String name, VotingMap mapSet)
	{
		if (name != null)
		{
			name = name.toLowerCase();
		}
		File file = new File(Config.ArenaImagesFolder, name + ".png");
		try
		{
			if (!file.exists())
			{
				file = new File(Config.ArenaImagesFolder, "default.png");
			}

			System.out.println("Loading... " + file.getName());
			Image image = ImageIO.read(file);
			List<MapView> views = mapSet.getMapViews();
			for (int i = 0; i < views.size(); i++)
			{
				MapView view = views.get(i);
				short key = view.getId();
				Image img = getScaledImage(image, i);
				images.put(key, img);
				needsChange.put(key, true);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player)
	{
		short id = mapView.getId();
		if (!needsChange.isEmpty() && needsChange.containsKey(id))
		{
			Image img = images.get(id);
			if (img != null)
			{
				mapCanvas.drawImage(0, 0, img);
			}
			needsChange.remove(id);
		}
	}
}
