export default function proxyBackend(
  request: Request,
  slug?: string,
): Promise<Response> {
  const backendUrl = process.env.BACKEND_URL ?? "http://localhost:8080";
  let proxyUrl = new URL(backendUrl);
  if (slug) {
    proxyUrl = new URL(slug, backendUrl);
  }
  const proxyRequest = new Request(proxyUrl, request);
  try {
    return fetch(proxyRequest);
  } catch (reason) {
    const message =
      reason instanceof Error ? reason.message : "Unexpected exception";
    return new Promise((resolve) => {
      resolve(new Response(message, { status: 500 }));
    });
  }
}
