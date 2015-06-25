package com.kiro.sg.utils.misc;

import java.io.*;

public final class FileUtils
{
	private FileUtils()
	{
	}

	public static void copyFolder(File src, File dest) throws IOException
	{
		System.out.println(src.getAbsolutePath());
		if (src.isDirectory())
		{
			if (!dest.exists())
			{
				dest.mkdir();
			}

			String[] files = src.list();
			for (String file : files)
			{
				File newSrc = new File(src, file);
				File newDest = new File(dest, file);

				copyFolder(newSrc, newDest);
			}
		}
		else if (!"uid.dat".equals(src.getName()))
		{
			try (InputStream in = new FileInputStream(src))
			{
				try (OutputStream out = new FileOutputStream(dest))
				{
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0)
					{
						out.write(buf, 0, len);
					}

					in.close();
					out.close();

				}
			}
		}

	}

	public static void deleteFolder(File src)
	{
		if (src.isDirectory())
		{
			String[] files = src.list();
			for (String file : files)
			{
				File nfile = new File(src, file);
				deleteFolder(nfile);
			}
		}
		src.delete();
	}
}
