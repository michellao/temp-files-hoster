import * as z from "zod/mini";

export default async function Version() {
  const backendUrl = process.env.BACKEND_URL;
  if (backendUrl) {
    const VersionModel = z.object({
      branch: z.string(),
      shortCommit: z.string(),
      commit: z.string(),
    });
    const response = await fetch(`${backendUrl}/app/version`, {
      cache: "force-cache",
    });
    try {
      const responseJson = await response.json();
      const appVersion = VersionModel.parse(responseJson);
      return <div>Build: {appVersion.commit}</div>;
    } catch (error: unknown) {
      console.error("error parsing", error);
    }
  }
}
